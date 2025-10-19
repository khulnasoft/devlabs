package com.khulnasoft.khulnasoftjenkins

import hudson.Extension
import hudson.FilePath
import hudson.Launcher
import hudson.model.AbstractBuild
import hudson.model.AbstractProject
import hudson.model.BuildListener
import hudson.model.Result
import hudson.model.Run
import hudson.model.TaskListener
import hudson.tasks.BuildStepDescriptor
import hudson.tasks.BuildStepMonitor
import hudson.tasks.Publisher
import hudson.tasks.Recorder
import hudson.util.FormValidation
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.QueryParameter

import javax.servlet.ServletException
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Khulnasoft Artifact Notifier - A Jenkins publisher that handles artifact archiving to Khulnasoft.
 * This notifier can be configured at the job level and provides an option to skip global Khulnasoft archiving.
 */
class KhulnasoftkArtifactNotifier extends Recorder {

    private static final Logger LOG = Logger.getLogger(KhulnasoftkArtifactNotifier.class.name)

    /**
     * If true, this job-level configuration will skip the global Khulnasoft archiving behavior.
     * This allows jobs to opt-out of automatic artifact archiving if they handle it differently.
     */
    boolean skipGlobalKhulnasoftArchive

    @DataBoundConstructor
    KhulnasoftkArtifactNotifier(boolean skipGlobalKhulnasoftArchive) {
        this.skipGlobalKhulnasoftArchive = skipGlobalKhulnasoftArchive
    }

    /**
     * Default constructor for backward compatibility
     */
    KhulnasoftkArtifactNotifier() {
        this(false)
    }

    @Override
    BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD
    }

    @Override
    boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        try {
            listener.logger.println("[Khulnasoft] Artifact notifier executing for build #${build.number}")

            // Get artifact configuration from job properties or use defaults
            def jobProperties = build.getProject().getProperties()
            def artifactIncludes = jobProperties.get('khulnasoft.artifact.includes', '**/*')
            def artifactExcludes = jobProperties.get('khulnasoft.artifact.excludes', '')
            def uploadFromSlave = jobProperties.get('khulnasoft.artifact.uploadFromSlave', 'false').toBoolean()

            listener.logger.println("[Khulnasoft] Artifact configuration - includes: ${artifactIncludes}, excludes: ${artifactExcludes}")

            // Archive artifacts using the global archiving mechanism
            def workspace = build.getWorkspace()
            if (workspace != null) {
                def runDelegate = new RunDelegate(build, build.getEnvironment(listener), listener)
                def fileSizeLimit = jobProperties.get('khulnasoft.artifact.fileSizeLimit', '')

                listener.logger.println("[Khulnasoft] Starting artifact archiving...")
                def archiveResult = runDelegate.archive(artifactIncludes, artifactExcludes, uploadFromSlave, fileSizeLimit)

                if (archiveResult) {
                    listener.logger.println("[Khulnasoft] Artifact archiving completed successfully")
                } else {
                    listener.logger.println("[Khulnasoft] Artifact archiving was skipped or failed")
                }
            } else {
                listener.logger.println("[Khulnasoft] No workspace available for artifact archiving")
            }

            return true
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error during Khulnasoft artifact archiving", e)
            listener.logger.println("[Khulnasoft] Error during artifact archiving: ${e.getMessage()}")
            return false
        }
    }

    @Extension
    static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true
        }

        @Override
        String getDisplayName() {
            return "Send files to Khulnasoft"
        }

        /**
         * Validate the skip global archive setting
         */
        FormValidation doCheckSkipGlobalKhulnasoftArchive(@QueryParameter boolean value) throws IOException, ServletException {
            // No specific validation needed for boolean
            return FormValidation.ok()
        }
    }
}
