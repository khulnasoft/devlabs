package com.khulnasoft.khulnasoftjenkins

import hudson.Extension
import hudson.model.Descriptor
import hudson.tools.ToolDescriptor
import hudson.tools.ToolInstallation
import hudson.tools.ToolProperty
import hudson.util.FormValidation
import jenkins.model.Jenkins
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.DataBoundSetter
import org.kohsuke.stapler.QueryParameter

import javax.servlet.ServletException
import java.io.IOException
import java.util.logging.Logger

/**
 * Represents a Khulnasoft installation configuration for Jenkins.
 * This class manages metadata sources for different types of events and reports.
 */
class KhulnasoftJenkinsInstallation extends ToolInstallation {

    private static final Logger LOG = Logger.getLogger(KhulnasoftJenkinsInstallation.class.name)

    // Default metadata source configurations for different event types
    private static final Map<String, String> DEFAULT_METADATA_SOURCES = [
        'test': 'jenkins.test.report',
        'coverage': 'jenkins.coverage.report',
        'notifications': 'jenkins.notifications',
        'build_metrics': 'jenkins.build.metrics',
        'default': 'jenkins.event.source'
    ]

    // Configurable metadata sources - can be overridden by system properties or Jenkins configuration
    private Map<String, String> metadataSources = new HashMap<>(DEFAULT_METADATA_SOURCES)

    // Custom metadata sources configured through UI
    private Map<String, String> customMetadataSources = new HashMap<>()

    @DataBoundConstructor
    KhulnasoftJenkinsInstallation(String name, String home, List<? extends ToolProperty<?>> properties) {
        super(name, home, properties)
        initializeMetadataSources()
    }

    /**
     * Set custom metadata sources from Jenkins configuration
     */
    @DataBoundSetter
    void setCustomMetadataSources(Map<String, String> customSources) {
        if (customSources != null) {
            this.customMetadataSources = new HashMap<>(customSources)
            // Merge custom sources with defaults, custom sources take precedence
            metadataSources.putAll(customSources)
            LOG.info("Updated metadata sources from configuration: ${customSources}")
        }
    }

    /**
     * Get custom metadata sources
     */
    Map<String, String> getCustomMetadataSources() {
        return new HashMap<>(customMetadataSources)
    }

    /**
     * Initialize metadata sources from configuration or use defaults
     */
    private void initializeMetadataSources() {
        // First load defaults
        metadataSources = new HashMap<>(DEFAULT_METADATA_SOURCES)

        // Override with system properties
        metadataSources.each { type, defaultSource ->
            String configuredSource = System.getProperty("khulnasoft.metadata.source.${type}")
            if (configuredSource) {
                metadataSources[type] = configuredSource
                LOG.info("Using configured metadata source for ${type}: ${configuredSource}")
            }
        }

        // Override with custom configuration (highest priority)
        metadataSources.putAll(customMetadataSources)
    }

    /**
     * Get the metadata source name for a specific event type
     * @param eventType The type of event (test, coverage, notifications, build_metrics, etc.)
     * @return The metadata source name to use for this event type
     */
    String getMetadataSource(String eventType) {
        String source = metadataSources.get(eventType)
        if (source == null) {
            source = metadataSources.get('default')
            LOG.warning("Unknown event type '${eventType}', using default metadata source: ${source}")
        }
        return source
    }

    /**
     * Set a custom metadata source for a specific event type
     * @param eventType The event type
     * @param source The metadata source name
     */
    void setMetadataSource(String eventType, String source) {
        metadataSources.put(eventType, source)
        customMetadataSources.put(eventType, source)
    }

    /**
     * Get all configured metadata sources
     * @return Map of event types to metadata sources
     */
    Map<String, String> getAllMetadataSources() {
        return new HashMap<>(metadataSources)
    }

    /**
     * Get the singleton instance of KhulnasoftJenkinsInstallation
     * @return The Khulnasoft installation instance
     */
    static KhulnasoftJenkinsInstallation get() {
        def jenkins = Jenkins.getInstance()
        def descriptor = jenkins.getDescriptor(KhulnasoftJenkinsInstallation)
        def installations = descriptor.getInstallations()

        if (installations.length == 0) {
            LOG.warning("No Khulnasoft installation configured, using default configuration")
            return new KhulnasoftJenkinsInstallation("default", "", Collections.emptyList())
        }

        return installations[0]
    }

    @Extension
    static class DescriptorImpl extends ToolDescriptor<KhulnasoftJenkinsInstallation> {

        @Override
        String getDisplayName() {
            return "Khulnasoft Installation"
        }

        @Override
        KhulnasoftJenkinsInstallation[] getInstallations() {
            return super.getInstallations() as KhulnasoftJenkinsInstallation[]
        }

        /**
         * Validate the installation name
         */
        FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {
            if (value == null || value.trim().isEmpty()) {
                return FormValidation.error("Installation name is required")
            }
            return FormValidation.ok()
        }

        /**
         * Validate the installation home directory
         */
        FormValidation doCheckHome(@QueryParameter String value) throws IOException, ServletException {
            if (value == null || value.trim().isEmpty()) {
                return FormValidation.warning("Installation home is optional")
            }
            return FormValidation.ok()
        }
    }
}
