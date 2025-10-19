package com.khulnasoft.khulnasoftjenkins;

/**
 * Internationalization messages for the Khulnasoft Jenkins plugin
 */
public class Messages {

    private Messages() {
        // Utility class
    }

    public static String PleaseProvideHost() {
        return "Please provide the hostname to a Khulnasoft instance.";
    }

    public static String HostNameSchemaWarning(String domain) {
        return "Invalid hostname, do you mean " + domain + "?";
    }

    public static String HostNameInvalid() {
        return "Failed to validate hostname";
    }

    public static String CloudHostPrefix(String hostName) {
        return "You are using Khulnasoft Cloud, please provide host name starts input- or http-inputs-, please try input-" + hostName + " or http-inputs-" + hostName + ". See also http://dev.khulnasoft.com/view/event-collector/SP-CAAAE7G";
    }

    public static String InvalidToken() {
        return "Token is invalid";
    }

    public static String InvalidHostOrToken() {
        return "Invalid config, please check Hostname or Token";
    }

    public static String InvalidPattern() {
        return "The pattern is invalid, please check <a href=\"https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html\">Pattern</a>";
    }

    public static String SplunArtifactArchive() {
        return "Send files to Khulnasoft";
    }
}
