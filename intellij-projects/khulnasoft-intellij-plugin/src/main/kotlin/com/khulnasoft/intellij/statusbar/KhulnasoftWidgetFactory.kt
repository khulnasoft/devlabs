/*
 * Copyright Khulnasoft, Ltd.
 */

package com.khulnasoft.intellij.statusbar

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory

class KhulnasoftWidgetFactory : StatusBarEditorBasedWidgetFactory() {
  override fun getId(): String {
    return "KhulnasoftWidget"
  }

  override fun getDisplayName(): String {
    return "Khulnasoft"
  }

  override fun createWidget(project: Project): StatusBarWidget {
    return KhulnasoftWidget(project)
  }

  override fun disposeWidget(widget: StatusBarWidget) {}
}
