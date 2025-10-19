package com.khulnasoft.khulnasoftjenkins.model;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Represents a metadata configuration item for Khulnasoft events
 */
public class MetaDataConfigItem {
    private String keyName;
    private String value;

    public MetaDataConfigItem() {
    }

    public MetaDataConfigItem(String keyName, String value) {
        this.keyName = keyName;
        this.value = value;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Load metadata configuration items from properties string
     * @param propsString the properties string in format "key1=value1\nkey2=value2"
     * @return set of metadata config items
     */
    public static Set<MetaDataConfigItem> loadProps(String propsString) {
        Set<MetaDataConfigItem> items = new HashSet<>();
        if (propsString == null || propsString.trim().isEmpty()) {
            return items;
        }

        Properties props = new Properties();
        try {
            props.load(new java.io.StringReader(propsString));
        } catch (Exception e) {
            // If loading fails, try to parse manually
            StringTokenizer st = new StringTokenizer(propsString, "\n");
            while (st.hasMoreTokens()) {
                String line = st.nextToken().trim();
                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        props.setProperty(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        }

        for (String key : props.stringPropertyNames()) {
            items.add(new MetaDataConfigItem(key, props.getProperty(key)));
        }

        return items;
    }

    /**
     * Convert set of metadata items to string format
     * @param items the set of items
     * @return string representation
     */
    public static String toString(Set<MetaDataConfigItem> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (MetaDataConfigItem item : items) {
            if (item.getKeyName() != null && item.getValue() != null) {
                sb.append(item.getKeyName()).append("=").append(item.getValue()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaDataConfigItem that = (MetaDataConfigItem) o;

        if (keyName != null ? !keyName.equals(that.keyName) : that.keyName != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = keyName != null ? keyName.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MetaDataConfigItem{" +
                "keyName='" + keyName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
