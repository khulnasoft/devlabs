package com.khulnasoft.khulnasoftjenkins.listeners

import hudson.Extension
import hudson.model.TaskListener
import hudson.model.listeners.RunListener
import hudson.model.AbstractBuild
import hudson.model.Run
import com.khulnasoft.khulnasoftjenkins.KhulnasoftJenkinsInstallation
import com.khulnasoft.khulnasoftjenkins.utils.KhulnasoftLogService
import com.khulnasoft.khulnasoftjenkins.model.EventType

import java.util.logging.Level
import java.util.logging.Logger

/**
 * Logging Run Listener for Khulnasoft Jenkins Plugin.
 * This listener monitors build events and sends structured log events to Khulnasoft.
 * It provides enhanced logging capabilities for build lifecycle events.
 */
@Extension
class LoggingRunListener extends RunListener<Run> {

    private static final Logger LOG = Logger.getLogger(LoggingRunListener.class.name)

    @Override
    void onStarted(Run run, TaskListener listener) {
        try {
            LOG.info("Build started: ${run.getParent().getFullName()} #${run.getNumber()}")

            // Send build start event to Khulnasoft if logging is enabled
            if (isKhulnasoftLoggingEnabled(run)) {
                def buildEvent = createBuildEvent(run, "started")
                sendToKhulnasoft(buildEvent, "build.lifecycle")
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error in onStarted for build ${run.getNumber()}", e)
        }
    }

    @Override
    void onCompleted(Run run, TaskListener listener) {
        try {
            def buildResult = run.getResult()?.toString() ?: "UNKNOWN"
            LOG.info("Build completed: ${run.getParent().getFullName()} #${run.getNumber()} - Result: ${buildResult}")

            // Send build completion event to Khulnasoft if logging is enabled
            if (isKhulnasoftLoggingEnabled(run)) {
                def buildEvent = createBuildEvent(run, "completed")
                buildEvent.put("result", buildResult)
                buildEvent.put("duration_ms", run.getDuration())

                sendToKhulnasoft(buildEvent, "build.lifecycle")
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error in onCompleted for build ${run.getNumber()}", e)
        }
    }

    @Override
    void onFinalized(Run run) {
        try {
            LOG.fine("Build finalized: ${run.getParent().getFullName()} #${run.getNumber()}")

            // Send build finalization event to Khulnasoft if logging is enabled
            if (isKhulnasoftLoggingEnabled(run)) {
                def buildEvent = createBuildEvent(run, "finalized")
                sendToKhulnasoft(buildEvent, "build.lifecycle")
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error in onFinalized for build ${run.getNumber()}", e)
        }
    }

    /**
     * Check if Khulnasoft logging is enabled for this build/project
     */
    private boolean isKhulnasoftLoggingEnabled(Run run) {
        try {
            def projectProperties = run.getParent().getProperties()
            def loggingEnabled = projectProperties.get('khulnasoft.logging.enabled', 'true').toBoolean()

            // Check global Khulnasoft installation for logging configuration
            def installation = KhulnasoftJenkinsInstallation.get()
            return loggingEnabled && installation != null
        } catch (Exception e) {
            LOG.log(Level.FINE, "Error checking Khulnasoft logging enabled status", e)
            return false
        }
    }

    /**
     * Create a standardized build event map
     */
    private Map createBuildEvent(Run run, String eventType) {
        def event = [:]
        event.put("event_type", eventType)
        event.put("build_number", run.getNumber())
        event.put("job_name", run.getParent().getFullName())
        event.put("build_url", run.getUrl())
        event.put("timestamp", System.currentTimeMillis())

        if (run instanceof AbstractBuild) {
            def build = (AbstractBuild) run
            event.put("build_id", build.getId())
            event.put("workspace", build.getWorkspace()?.getRemote() ?: "")
        }

        return event
    }

    /**
     * Send event to Khulnasoft logging system
     */
    private void sendToKhulnasoft(Map event, String metadataSource) {
        try {
            def installation = KhulnasoftJenkinsInstallation.get()
            if (installation != null) {
                def sourceName = installation.getMetadataSource(metadataSource)
                KhulnasoftLogService.getInstance().send(event, EventType.BUILD_REPORT, sourceName)
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error sending event to Khulnasoft", e)
        }
    }
}
