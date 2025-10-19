package com.khulnasoft.khulnasoftjenkins.model;

/**
 * Enumeration of different event types that can be sent to Khulnasoft
 */
public enum EventType {
    CONSOLE_LOG("console", "console_log", true),
    JENKINS_CONFIG("jenkins_config", "jenkins_config", false),
    LOG("log", "log", false),
    BUILD_REPORT("build_report", "build_report", false),
    QUEUE_INFO("queue_info", "queue_info", false),
    SLAVE_INFO("slave_info", "slave_info", false);

    private final String key;
    private final String displayName;
    private final boolean needSplit;

    EventType(String key, String displayName, boolean needSplit) {
        this.key = key;
        this.displayName = displayName;
        this.needSplit = needSplit;
    }

    /**
     * Get the key used for metadata configuration
     * @param suffix additional suffix for the key
     * @return the key with suffix if provided
     */
    public String getKey(String suffix) {
        if (suffix != null && !suffix.isEmpty()) {
            return key + "_" + suffix;
        }
        return key;
    }

    /**
     * Get the key used for metadata configuration
     * @return the base key
     */
    public String getKey() {
        return getKey(null);
    }

    /**
     * Get the display name for this event type
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Check if this event type needs to be split into multiple events
     * @return true if events of this type should be split
     */
    public boolean needSplit() {
        return needSplit;
    }

    /**
     * Get EventType by key
     * @param key the key to look for
     * @return the EventType or null if not found
     */
    public static EventType fromKey(String key) {
        for (EventType type : values()) {
            if (type.key.equals(key)) {
                return type;
            }
        }
        return null;
    }
}
