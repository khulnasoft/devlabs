/*
 * Copyright Khulnasoft, Ltd.
 */

package com.khulnasoft.intellij.auth

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware

class ProvideAuthTokenAction : AnAction(), DumbAware {
  override fun actionPerformed(e: AnActionEvent) {
    e.project ?: return
    service<KhulnasoftAuthService>().provideAuthToken(e.project!!) {}
  }

  override fun update(e: AnActionEvent) {
    e.presentation.isEnabledAndVisible = !(service<KhulnasoftAuthService>().loggedIn.get())
    e.presentation.text = "Provide Auth Token to Khulnasoft"
  }
}
