/*
 * Copyright Khulnasoft, Ltd.
 */

package com.khulnasoft.intellij.statusbar

import com.khulnasoft.intellij.KhulnasoftNotification
import com.khulnasoft.intellij.Language.isWellSupportedLanguage
import com.khulnasoft.intellij.language_server.LanguageServerService
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.ide.util.PropertiesComponent
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.wm.WindowManager
import ksoft.khulnasoft_common_pb.KhulnasoftCommon
import ksoft.language_server_pb.LanguageServer
import io.grpc.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

const val PERSISTED_LANGUAGE_CONFIGURATION_NAME = "khulnasoft.language_configuration"
const val PERSISTED_API_SERVER_URL_NAME = "com.khulnasoft.apiServerUrl"
const val PERSISTED_PORTAL_URL_NAME = "com.khulnasoft.portalUrl"

const val ENTERPRISE_PLUGIN_ID = "com.khulnasoft.enterprise"
const val STANDARD_PLUGIN_ID = "com.khulnasoft.intellij"

const val DEFAULT_API_SERVER_URL = "https://server.khulnasoft.com"
const val DEFAULT_WEBSITE_URL = "https://www.khulnasoft.com"

class KhulnasoftStatusService {
  private var status: LanguageServer.State? = null
  private var popupMessage: String? = null
  private var khulnasoftLanguageConfig = mutableMapOf<String, Boolean>()
  private val logger = logger<KhulnasoftStatusService>()

  private var shownAbortErrorNotification = false

  init {
    val propertiesComponent = PropertiesComponent.getInstance()
    val languageConfiguration = propertiesComponent.getValue(PERSISTED_LANGUAGE_CONFIGURATION_NAME)
    if (languageConfiguration != null) {
      val parser = JSONParser()
      val json = parser.parse(languageConfiguration) as JSONObject
      json.forEach { (key, value) -> khulnasoftLanguageConfig[key as String] = value as Boolean }
    } else {
      khulnasoftLanguageConfig["*"] = true
    }
  }

  fun hasEnterprisePlugin(): Boolean {
    val enterpriseKhulnasoftPlugin = PluginManagerCore.getPlugin(PluginId.getId(ENTERPRISE_PLUGIN_ID))
    return enterpriseKhulnasoftPlugin != null
  }

  fun getApiServerUrl(): String {
    if (hasEnterprisePlugin()) {
      val propertiesComponent = PropertiesComponent.getInstance()
      val apiServerUrl = propertiesComponent.getValue(PERSISTED_API_SERVER_URL_NAME)
      if (apiServerUrl != null) {
        return apiServerUrl
      } else {
        logger.warn("$PERSISTED_API_SERVER_URL_NAME is not set for the enterprise plugin")
      }
    }
    return DEFAULT_API_SERVER_URL
  }

  fun getWebsiteUrl(): String {
    if (hasEnterprisePlugin()) {
      val propertiesComponent = PropertiesComponent.getInstance()
      val portalUrl = propertiesComponent.getValue(PERSISTED_PORTAL_URL_NAME)
      if (portalUrl != null) {
        return portalUrl
      } else {
        logger.warn("$PERSISTED_PORTAL_URL_NAME is not set for the enterprise plugin")
      }
    }
    return DEFAULT_WEBSITE_URL
  }

  fun getVersion(): String {
    if (hasEnterprisePlugin()) {
      return PluginManagerCore.getPlugin(PluginId.getId(ENTERPRISE_PLUGIN_ID))?.version ?: ""
    }
    return PluginManagerCore.getPlugin(PluginId.getId(STANDARD_PLUGIN_ID))?.version ?: ""
  }

  fun toggleKhulnasoftEnabled(languageId: String = "*") {
    val eventJson = JSONObject()
    eventJson["languageId"] = languageId
    val copilot = PluginManagerCore.getPlugin(PluginId.getId("com.github.copilot"))
    eventJson["copilotInstalled"] = copilot != null
    eventJson["copilotEnabled"] = copilot?.isEnabled ?: false

    val isKhulnasoftEnabled = isKhulnasoftEnabled(languageId)
    val eventType =
        if (isKhulnasoftEnabled) KhulnasoftCommon.EventType.EVENT_TYPE_DISABLE_KHULNASOFT
        else KhulnasoftCommon.EventType.EVENT_TYPE_ENABLE_KHULNASOFT

    // Toggle the language configuration.
    khulnasoftLanguageConfig[languageId] = !isKhulnasoftEnabled
    // If the current configuration equals the default configuration, remove it from the
    // configuration.
    if (khulnasoftLanguageConfig[languageId] == isWellSupportedLanguage(languageId)) {
      khulnasoftLanguageConfig.remove(languageId)
    }

    val languageServerService = service<LanguageServerService>()
    val event =
        KhulnasoftCommon.Event.newBuilder()
            .setEventType(eventType)
            .setEventJson(eventJson.toJSONString())
            .build()
    CoroutineScope(Dispatchers.IO).launch { languageServerService.recordEvent(event) }

    // Persist the language configuration
    val jsonObject = JSONObject()
    khulnasoftLanguageConfig.forEach { (key, value) -> jsonObject[key] = value }
    PropertiesComponent.getInstance()
        .setValue(PERSISTED_LANGUAGE_CONFIGURATION_NAME, jsonObject.toJSONString())

    updateWidgets()
  }

  fun isKhulnasoftEnabled(languageId: String): Boolean {
    // If all languages are disabled, return false
    if (khulnasoftLanguageConfig["*"] == false) {
      return false
    }

    // If the language is explicitly set, return that value, otherwise return whether it is
    // enabled by default.
    return khulnasoftLanguageConfig.getOrElse(languageId) { isWellSupportedLanguage(languageId) }
  }

  fun updateState(status: LanguageServer.State) {
    this.status = status
    this.popupMessage = null
    updateWidgets()
  }

  fun updateState(state: LanguageServer.KhulnasoftState) {
    this.status = LanguageServer.State.newBuilder().setState(state).build()
    this.popupMessage = null
    updateWidgets()
  }

  private fun updateWidgets() {
    ProjectManager.getInstance().openProjects.forEach { project ->
      val statusBar = WindowManager.getInstance().getStatusBar(project)
      val widget = statusBar?.getWidget("KhulnasoftWidget") ?: return@forEach
      if (widget is KhulnasoftWidget) {
        widget.update { statusBar.updateWidget("KhulnasoftWidget") }
      }
    }
  }

  fun getState(): LanguageServer.State? {
    return status
  }

  fun showAbortErrorNotification(status: Status, editor: Editor) {
    if (shownAbortErrorNotification) {
      return
    }
    val notification =
        KhulnasoftNotification(
            "com.khulnasoft",
            "Khulnasoft",
            status.description ?: "Unknown error",
            NotificationType.WARNING)
    notification.notify(editor.project)
    shownAbortErrorNotification = true
  }
}
