/*
 * Copyright Khulnasoft, Ltd.
 */

package com.khulnasoft.intellij.statusbar

import com.khulnasoft.intellij.auth.*
import com.khulnasoft.intellij.icons.KhulnasoftIcons
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup
import com.intellij.psi.PsiManager
import com.intellij.ui.AnimatedIcon
import ksoft.language_server_pb.LanguageServer
import java.util.concurrent.TimeUnit

class KhulnasoftWidget(project: Project) : EditorBasedStatusBarPopup(project, false) {
  override fun ID(): String {
    return "KhulnasoftWidget"
  }

  override fun getWidgetState(file: VirtualFile?): WidgetState {
    val psiFile = file?.let { PsiManager.getInstance(project).findFile(it) }
    val languageId = psiFile?.language?.id ?: "*"
    if (!service<KhulnasoftStatusService>().isKhulnasoftEnabled(languageId)) {
      val tooltipText = "Click to enable Khulnasoft"
      val icon = KhulnasoftIcons.KhulnasoftIconWarning
      val state = WidgetState(tooltipText, "", true)
      state.icon = icon
      return state
    }

    val state = service<KhulnasoftStatusService>().getState()
    val tooltipText =
        if (state?.message.isNullOrEmpty()) {
          "Khulnasoft"
        } else {
          state?.message!!
        }
    val icon =
        when (state?.state ?: LanguageServer.KhulnasoftState.KHULNASOFT_STATE_UNSPECIFIED) {
          LanguageServer.KhulnasoftState.KHULNASOFT_STATE_INACTIVE -> KhulnasoftIcons.KhulnasoftIcon
          LanguageServer.KhulnasoftState.KHULNASOFT_STATE_SUCCESS -> KhulnasoftIcons.KhulnasoftIcon
          LanguageServer.KhulnasoftState.KHULNASOFT_STATE_PROCESSING -> AnimatedIcon.Default.INSTANCE
          LanguageServer.KhulnasoftState.KHULNASOFT_STATE_WARNING -> KhulnasoftIcons.KhulnasoftIconWarning
          LanguageServer.KhulnasoftState.KHULNASOFT_STATE_ERROR -> KhulnasoftIcons.KhulnasoftIconError
          else -> KhulnasoftIcons.KhulnasoftIcon
        }
    val widgetState = WidgetState(tooltipText, "", true)
    widgetState.icon = icon
    return widgetState
  }

  private fun getActionGroup(dataContext: DataContext): DefaultActionGroup {
    val actionGroup = DefaultActionGroup()
    if (service<KhulnasoftAuthService>().loggedIn.get()) {
      actionGroup.add(LogoutAction())
    } else {
      actionGroup.add(LoginAction())
      // Don't show the "Enable/Disable Khulnasoft" actions if the user is not logged in
      return actionGroup
    }
    val khulnasoftStatusService = service<KhulnasoftStatusService>()
    actionGroup.addSeparator()
    val file = dataContext.getData(PSI_FILE)
    val languageId = file?.language?.id
    val language = file?.language?.displayName

    if (khulnasoftStatusService.isKhulnasoftEnabled("*") &&
        (languageId == null || khulnasoftStatusService.isKhulnasoftEnabled(languageId))) {
      actionGroup.add(DisableKhulnasoftAction())
    } else if (!khulnasoftStatusService.isKhulnasoftEnabled("*")) {
      actionGroup.add(EnableKhulnasoftAction())
    }
    if (languageId != null && language != null) {
      if (khulnasoftStatusService.isKhulnasoftEnabled(languageId)) {
        actionGroup.add(DisableKhulnasoftAction(languageId, language))
      } else if (khulnasoftStatusService.isKhulnasoftEnabled("*") &&
          !khulnasoftStatusService.isKhulnasoftEnabled(languageId)) {
        actionGroup.add(EnableKhulnasoftAction(languageId, language))
      }
    }
    return actionGroup
  }

  override fun createPopup(context: DataContext): ListPopup? {
    val dataContext =
        DataManager.getInstance().dataContextFromFocusAsync.blockingGet(100, TimeUnit.MILLISECONDS)
            ?: return null
    val actionGroup = getActionGroup(dataContext)
    return JBPopupFactory.getInstance()
        .createActionGroupPopup(
            "Khulnasoft",
            actionGroup,
            dataContext,
            JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
            false)
  }

  override fun createInstance(project: Project): StatusBarWidget {
    return KhulnasoftWidget(project)
  }

  override fun dispose() {}
}
