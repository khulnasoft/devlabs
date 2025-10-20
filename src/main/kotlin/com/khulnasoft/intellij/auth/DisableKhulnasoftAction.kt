/*
 * Copyright Khulnasoft, Ltd.
 */

package com.khulnasoft.intellij.auth

import com.khulnasoft.intellij.Language.isWellSupportedLanguage
import com.khulnasoft.intellij.statusbar.KhulnasoftStatusService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PopupAction
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware

class DisableKhulnasoftAction(
    private val languageId: String = "*",
    private val language: String = ""
) : AnAction(), PopupAction, DumbAware {
  override fun actionPerformed(e: AnActionEvent) {
    val khulnasoftStatusService = service<KhulnasoftStatusService>()
    if (khulnasoftStatusService.isKhulnasoftEnabled(languageId)) {
      khulnasoftStatusService.toggleKhulnasoftEnabled(languageId)
    }
  }

  override fun update(e: AnActionEvent) {
    val experimental = if (isWellSupportedLanguage(languageId)) "" else " (experimental)"
    e.presentation.text =
        if (languageId == "*") {
          "Disable Khulnasoft Globally"
        } else {
          "Disable Khulnasoft for $language$experimental"
        }
  }
}
