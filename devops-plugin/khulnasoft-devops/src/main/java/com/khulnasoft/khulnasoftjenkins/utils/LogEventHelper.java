package com.khulnasoft.khulnasoftjenkins.utils;

import hudson.model.Job;
import hudson.model.Run;
import jenkins.model.Jenkins;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper utilities for log events and validation
 */
public class LogEventHelper {

    private static final Logger LOG = Logger.getLogger(LogEventHelper.class.getName());

    /**
     * Get default DSL script content
     * @return default script content
     */
    public static String getDefaultDslScript() {
        return """
            // Default Khulnasoft DSL script
            khulnasoftins.send([
                'event_tag': 'jenkins_build',
                'build_number': build.getNumber(),
                'job_name': build.getParent().getFullName(),
                'build_result': build.getResult()?.toString(),
                'build_duration': build.getDuration(),
                'build_url': build.getUrl(),
                'user_name': khulnasoftins.getTriggerUserName()
            ])
            """;
    }

    /**
     * Check if a string is not empty (not null and not blank)
     * @param str the string to check
     * @return true if not empty
     */
    public static boolean nonEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validate Groovy script syntax
     * @param script the script to validate
     * @return FormValidation result
     */
    public static hudson.util.FormValidation validateGroovyScript(String script) {
        if (!nonEmpty(script)) {
            return hudson.util.FormValidation.ok();
        }

        try {
            // Basic syntax check - try to compile the script
            groovy.lang.GroovyShell shell = new groovy.lang.GroovyShell();
            shell.parse(script);
            return hudson.util.FormValidation.ok();
        } catch (Exception e) {
            return hudson.util.FormValidation.error("Script syntax error: " + e.getMessage());
        }
    }

    /**
     * Verify HTTP input connection to Khulnasoft
     * @param config the Khulnasoft configuration
     * @return FormValidation result
     */
    public static hudson.util.FormValidation verifyHttpInput(Object config) {
        try {
            // This is a placeholder - in a real implementation this would test the connection
            // For now, just return OK if we have valid configuration
            return hudson.util.FormValidation.ok("Connection test not implemented in this version");
        } catch (Exception e) {
            return hudson.util.FormValidation.error("Connection test failed: " + e.getMessage());
        }
    }

    /**
     * Get build variables for environment
     * @param build the build
     * @return map of build variables
     */
    public static Map<String, String> getBuildVariables(Run build) {
        Map<String, String> variables = new HashMap<>();
        try {
            if (build != null) {
                variables.putAll(build.getEnvironment(TaskListener.NULL));
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Failed to get build variables", e);
        }
        return variables;
    }

    /**
     * Check if project has a specific publisher
     * @param shortClassName the short class name of the publisher
     * @param build the build
     * @return true if publisher exists
     */
    public static boolean hasPublisherName(String shortClassName, Run build) {
        if (build == null) {
            return false;
        }

        try {
            for (Object action : build.getAllActions()) {
                if (action.getClass().getName().endsWith(shortClassName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            LOG.log(Level.FINE, "Error checking for publisher: " + shortClassName, e);
        }

        return false;
    }

    /**
     * Parse file size string to bytes
     * @param sizeStr size string like "10MB", "500KB", "1GB"
     * @return size in bytes, or -1 if invalid
     */
    public static long parseFileSize(String sizeStr) {
        if (sizeStr == null || sizeStr.trim().isEmpty()) {
            return -1;
        }

        try {
            sizeStr = sizeStr.trim().toUpperCase();
            long multiplier = 1;

            if (sizeStr.endsWith("KB")) {
                multiplier = 1024;
                sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
            } else if (sizeStr.endsWith("MB")) {
                multiplier = 1024 * 1024;
                sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
            } else if (sizeStr.endsWith("GB")) {
                multiplier = 1024 * 1024 * 1024;
                sizeStr = sizeStr.substring(0, sizeStr.length() - 2);
            }

            return Long.parseLong(sizeStr) * multiplier;
        } catch (NumberFormatException e) {
            LOG.log(Level.WARNING, "Invalid file size format: " + sizeStr, e);
            return -1;
        }
    }

    /**
     * Send files to Khulnasoft
     * @param build the build
     * @param workspace the workspace
     * @param envVars environment variables
     * @param listener task listener
     * @param includes include pattern
     * @param excludes exclude pattern
     * @param uploadFromSlave upload from slave
     * @param maxFileSize max file size
     * @return number of files sent
     */
    public static int sendFiles(Run build, Object workspace, Map<String, String> envVars,
                               Object listener, String includes, String excludes,
                               boolean uploadFromSlave, long maxFileSize) {
        // This is a placeholder implementation
        // In a real implementation, this would handle file archiving
        LOG.info("sendFiles called - implementation placeholder");
        return 0;
    }

    /**
     * Get trigger user name for build
     * @param build the build
     * @return user name or "unknown"
     */
    public static String getTriggerUserName(Run build) {
        try {
            if (build != null) {
                Object cause = build.getCause(hudson.model.Cause.UserIdCause.class);
                if (cause != null) {
                    return ((hudson.model.Cause.UserIdCause) cause).getUserName();
                }
            }
        } catch (Exception e) {
            LOG.log(Level.FINE, "Error getting trigger user name", e);
        }
        return "unknown";
    }

    /**
     * Get master statistics
     * @return map of master stats
     */
    public static Map<String, Object> getMasterStats() {
        Map<String, Object> stats = new HashMap<>();
        try {
            stats.put("node_type", "master");
            stats.put("status", "online");
            stats.put("timestamp", System.currentTimeMillis());
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error getting master stats", e);
        }
        return stats;
    }

    /**
     * Get slave statistics
     * @return map of slave stats by node name
     */
    public static Map<String, Map<String, Object>> getSlaveStats() {
        Map<String, Map<String, Object>> slaveStats = new HashMap<>();
        try {
            Jenkins jenkins = Jenkins.getInstanceOrNull();
            if (jenkins != null) {
                for (hudson.model.Computer computer : jenkins.getComputers()) {
                    if (computer instanceof hudson.model.Slave.SlaveComputer) {
                        Map<String, Object> stats = new HashMap<>();
                        stats.put("node_type", "slave");
                        stats.put("status", computer.isOnline() ? "online" : "offline");
                        stats.put("timestamp", System.currentTimeMillis());
                        slaveStats.put(computer.getName(), stats);
                    }
                }
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error getting slave stats", e);
        }
        return slaveStats;
    }

    /**
     * Get running jobs
     * @return list of running job info
     */
    public static List<Map<String, Object>> getRunningJob() {
        List<Map<String, Object>> runningJobs = new ArrayList<>();
        try {
            Jenkins jenkins = Jenkins.getInstanceOrNull();
            if (jenkins != null) {
                for (Job job : jenkins.getAllItems(Job.class)) {
                    if (job.isBuilding()) {
                        hudson.model.Run lastBuild = job.getLastBuild();
                        if (lastBuild != null && lastBuild.isBuilding()) {
                            Map<String, Object> jobInfo = new HashMap<>();
                            jobInfo.put("job_name", job.getFullName());
                            jobInfo.put("build_number", lastBuild.getNumber());
                            jobInfo.put("status", "running");
                            jobInfo.put("start_time", lastBuild.getStartTimeInMillis());
                            runningJobs.add(jobInfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error getting running jobs", e);
        }
        return runningJobs;
    }

    /**
     * Get build version
     * @return version string
     */
    public static String getBuildVersion() {
        // This would typically come from a properties file or build info
        return "1.0.0-SNAPSHOT";
    }
}
