package com.khulnasoft.intellij

import com.khulnasoft.intellij.auth.KhulnasoftAuthService
import com.khulnasoft.intellij.language_server.LanguageServerService
import com.khulnasoft.intellij.settings.AppSettingsState
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootEvent
import com.intellij.openapi.roots.ModuleRootListener
import com.intellij.openapi.roots.ModuleRootManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// Listener for module root change events which fire when modules are added/removed
// and when content roots are added/removed for a given module.
class KhulnasoftModuleListener : ModuleRootListener {
  override fun rootsChanged(event: ModuleRootEvent) {
    if (!AppSettingsState.instance.indexingEnabled && !service<KhulnasoftAuthService>().isTeams) {
      return
    }
    service<KhulnasoftWorkspaceTracker>().updateRootsForProject(event.project)
  }
}

// Tracks currently active workspaces, and fires Add/Remove TrackedWorkspace requests to
// the language server as necessary
@Service(Service.Level.PROJECT)
class KhulnasoftWorkspaceTracker() {
  private val mu = Mutex()

  private var trackedWorkspaces = mutableSetOf<String>()
  private var languageServerService = service<LanguageServerService>()

  suspend fun setRoots(roots: Set<String>) {
    if (!languageServerService.languageServerStarted()) {
      return
    }
    if (!AppSettingsState.instance.indexingEnabled && !service<KhulnasoftAuthService>().isTeams) {
      return
    }
    var toRemove: Set<String>
    var toAdd: Set<String>
    mu.withLock {
      toRemove = trackedWorkspaces.minus(roots)
      toAdd = roots.minus(trackedWorkspaces)
      trackedWorkspaces = roots.toMutableSet()
    }
    for (workspace in toRemove) {
      languageServerService.removeTrackedWorkspace(workspace)
    }
    for (workspace in toAdd) {
      languageServerService.addTrackedWorkspace(workspace)
    }
  }

  // Given a project, get the latest content roots for that project and update the trackedWorkspaces
  // for the listener, submitting Add/RemoveTrackedWorkspace to the language server as necessary.
  // A project can have multiple modules. Each module can have multiple "Content Roots".
  // Workspaces are most analagous to Module ContentRoot
  fun updateRootsForProject(project: Project) {
    val modules = ModuleManager.getInstance(project).sortedModules
    var rootPaths = mutableSetOf<String>()
    for (module in modules) {
      val rootManager = ModuleRootManager.getInstance(module)
      val rootFiles = rootManager.getContentRoots()
      rootFiles.map { rootPaths.add(it.path) }
    }
    CoroutineScope(Dispatchers.IO).launch { setRoots(rootPaths) }
  }
}