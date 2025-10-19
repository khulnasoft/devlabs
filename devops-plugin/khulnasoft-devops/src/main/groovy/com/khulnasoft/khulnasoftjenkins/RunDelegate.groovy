package com.khulnasoft.khulnasoftjenkins

import com.khulnasoft.khulnasoftjenkins.listeners.LoggingRunListener
import com.khulnasoft.khulnasoftjenkins.model.CoverageMetricsAdapter
import com.khulnasoft.khulnasoftjenkins.model.EventType
import com.khulnasoft.khulnasoftjenkins.model.JunitTestCaseGroup
import com.khulnasoft.khulnasoftjenkins.utils.LogEventHelper
import com.khulnasoft.khulnasoftjenkins.utils.KhulnasoftLogService
import com.khulnasoft.khulnasoftjenkins.utils.TestCaseResultUtils
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import hudson.FilePath
import hudson.model.AbstractBuild
import hudson.model.Action
import hudson.model.Run
import hudson.model.TaskListener
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.Whitelisted

import java.util.logging.Level
import java.util.logging.Logger

import static com.khulnasoft.khulnasoftjenkins.Constants.BUILD_ID
import static com.khulnasoft.khulnasoftjenkins.Constants.TAG
import static com.khulnasoft.khulnasoftjenkins.Constants.JOB_RESULT
import static com.khulnasoft.khulnasoftjenkins.Constants.USER_NAME_KEY
import static com.khulnasoft.khulnasoftjenkins.Constants.BUILD_REPORT_ENV_TAG
import static com.khulnasoft.khulnasoftjenkins.utils.LogEventHelper.parseFileSize
import static com.khulnasoft.khulnasoftjenkins.utils.LogEventHelper.sendFiles
import static com.khulnasoft.khulnasoftjenkins.utils.LogEventHelper.getTriggerUserName


public class RunDelegate {
    static final LOG = Logger.getLogger(LoggingRunListener.class.name)

    Run build;
    Map env;
    TaskListener listener;
    FilePath workSpace;

    RunDelegate() {
    }

    public RunDelegate(Run build, Map env, TaskListener listener) {
        this.build = build;
        if (env != null) {
            this.env = env;
        } else {
            this.env = new HashMap();
        }
        this.listener = listener;
    }

    /**
     *
     * @param message
     * @return true if enqueue successfully, false if the message is discarded
     */
    def send(def message) {
        return KhulnasoftLogService.getInstance().send(message, EventType.BUILD_REPORT);
    }

    /**
     *
     * @param message the message to send
     * @param sourceName source for khulnasoft metadata
     * @return true if enqueue successfully, false if the message is discarded
     */
    def send(def message, String eventSourceName) {
        return KhulnasoftLogService.getInstance().send(message, EventType.BUILD_REPORT, eventSourceName);
    }

    /**
     * Archive all configured artifacts from a build, using ant patterns defined in
     * @see <a href="http://ant.apache.org/manual/Types/fileset.html">the Ant glob syntax</a>
     * such as  {@code *&#42;&#47;build/*.log }
     * @param includes ant glob pattern
     * @param excludes ant glob pattern
     * @param uploadFromSlave <code>true</code> if need upload directly from the slave
     * @parm fileSizeLimit max size per file to send to khulnasoft, to prevent sending huge files by wildcard includes
     */
    @Whitelisted
    def archive(String includes, String excludes = null, boolean uploadFromSlave = false, String fileSizeLimit = "") {
        if (build instanceof AbstractBuild) {
            def notifier = build.project.getPublishersList().get(KhulnasoftArtifactNotifier)
            if (notifier != null) {
                //already defined on job level
                if (notifier.skipGlobalKhulnasoftArchive) {
                    return;
                }
            }
        }
        return sendFiles(build, workSpace, env, listener, includes, excludes, uploadFromSlave, parseFileSize(fileSizeLimit));
    }

    @Whitelisted
    def getJunitReport() {
        //no pagination, use MAX_VALUE as page size
        List<JunitTestCaseGroup> results = getJunitReport(Integer.MAX_VALUE, null);
        if (!results.isEmpty()) {
            return results.get(0)
        } else {
            return results;
        }
    }

    @Whitelisted
    def getJunitReport(int pageSize, List<String> ignoredActions = null) {
        try {
            return TestCaseResultUtils.getBuildReport(build, pageSize, ignoredActions);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "failed to get junit report", ex)
            return Collections.emptyList();
        }
    }

    @Whitelisted
    def sendTestReport(int pageSize) {
        def startTime = System.currentTimeMillis()
        def results = getJunitReport(pageSize, null)
        def endTime = System.currentTimeMillis()
        def duration = endTime - startTime

        // Log metrics for monitoring
        LOG.info("Test report generated for build ${build.getNumber()} in ${duration}ms with ${results.size()} pages")

        def buildEvent = getBuildEvent()
        String sourceName = KhulnasoftJenkinsInstallation.get().getMetadataSource("test")
        results.eachWithIndex { junitResult, idx ->
            Map pagedEvent = buildEvent + ["testsuite": junitResult, "page_num": idx + 1, "generation_time_ms": duration]
            send(pagedEvent, sourceName)
        }
    }

    @Whitelisted
    @SuppressFBWarnings("SE_NO_SERIALVERSIONID")
    def sendCoverageReport(int pageSize) {
        def coverageList = CoverageMetricsAdapter.getReport(build, pageSize);
        if (coverageList.isEmpty()) {
            return;
        }
        String sourceName = KhulnasoftJenkinsInstallation.get().getMetadataSource("coverage")
        def buildEvent = getBuildEvent()
        buildEvent.put(TAG, "coverage")
        coverageList.eachWithIndex { coverage, idx ->
            Map pagedEvent = buildEvent + ["coverage": coverage, "page_num": idx + 1]
            send(pagedEvent, sourceName)
        }
    }

    def getOut() {
        return listener.logger
    }

    def println(String s) {
        try {
            getOut().println(s)
        } catch (Exception ex) {//shouldn't happen!
            ex.printStackTrace();
        }
    }

    def getEnv() {
        return env
    }

    def getBuild() {
        return build
    }

    /**
     * get specified actionName that contributed to build object.
     *
     * refer to
     * https://wiki.jenkins-ci.org/display/JENKINS/Plugins#Plugins-Buildreports
     * https://wiki.jenkins-ci.org/display/JENKINS/Plugins#Plugins-Otherpostbuildactions
     * @param className
     * @return
     */
    @Whitelisted
    def Action getActionByClassName(String className) {
        try {
            Class actionClz = Class.forName(className);
            if (!(Action.isAssignableFrom(actionClz))) {
                return null;
            }
            return build.getAction(actionClz);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }
    /**
     * Gets the action (first instance to be found) of a specified type that contributed to this build.
     *
     * @param type
     * @return The first action item or <code>null</code> if no such actions exist.
     *
     */
    @Whitelisted
    public Action getAction(Class<? extends Action> type) {
        return build.getAction(type);
    }

    /**
     * check if the project has publisher
     * @param shortClassName , common used publishers are junit.JUnitResultArchiver, testng.Publisher
     * @return
     */
    @Whitelisted
    def boolean hasPublisherName(String shortClassName) {
        return LogEventHelper.hasPublisherName(shortClassName, build);
    }

    @Override
    public String toString() {
        return "RunDelegate on build:" + this.build;
    }

    @Whitelisted
    def getBuildEvent() {
        String url = build.getUrl();
        Map event = new HashMap();
        event.put(TAG, "build_report")
        event.put(USER_NAME_KEY, getTriggerUserName(build));
        event.put(JOB_RESULT, build.getResult().toString());
        event.put(BUILD_ID, url);
        event.put(BUILD_REPORT_ENV_TAG, env);
        event.put("build_number", build.getNumber());
        event.put("job_name", build.getParent().getFullName());
        return event;
    }

    /**
     * <pre>
     * {@code
     * //modify the build the event on the fly
     * sendReport ({report - >
     * report["foo"] = "bar";
     * report["testsuite"] = junitReport;
     *})
     * //use a map as return
     * sendReport{
     *     return ["ke":"value"]
     *}*}</pre>
     * send build reports with build variables as metadata
     * @param closure Groovy closure with a Map as parameter
     */
    @Whitelisted
    def manageBuildCache() {
        def properties = build.getProject().getProperties()
        def cacheEnabled = properties.get('build.cache.enabled', 'true').toBoolean()
        def cacheStrategy = properties.get('build.cache.strategy', 'incremental')
        def maxCacheSize = properties.get('build.cache.max.size', '1GB')

        if (cacheEnabled) {
            LOG.info("Managing build cache for build ${build.getNumber()}: strategy=${cacheStrategy}, maxSize=${maxCacheSize}")
            // Example: Integrate with Jenkins cache plugins or external tools
            // This could involve setting up workspace caching or artifact reuse
            def cacheInfo = [
                strategy: cacheStrategy,
                maxSize: maxCacheSize,
                enabled: true
            ]
            return cacheInfo
        } else {
            LOG.info("Build cache disabled for build ${build.getNumber()}")
            return [enabled: false]
        }
    }

    @Whitelisted
    def checkPerformanceThresholds() {
        def properties = build.getProject().getProperties()
        def cpuThreshold = properties.get('build.performance.cpu.threshold', '80').toInteger()
        def memoryThreshold = properties.get('build.performance.memory.threshold', '70').toInteger()

        // Simulate checking current CPU and memory usage (in a real scenario, integrate with monitoring tools)
        def currentCpu = 50  // Placeholder: replace with actual monitoring API call
        def currentMemory = 60  // Placeholder: replace with actual monitoring API call

        def cpuExceeded = currentCpu > cpuThreshold
        def memoryExceeded = currentMemory > memoryThreshold

        if (cpuExceeded || memoryExceeded) {
            def message = "Performance threshold exceeded: CPU=${currentCpu}% (threshold=${cpuThreshold}%), Memory=${currentMemory}% (threshold=${memoryThreshold}%)"
            LOG.warning(message)
            sendNotification(message, "email")
        } else {
            LOG.info("Performance thresholds OK: CPU=${currentCpu}%, Memory=${currentMemory}%")
        }

        return [
            cpuThreshold: cpuThreshold,
            memoryThreshold: memoryThreshold,
            currentCpu: currentCpu,
            currentMemory: currentMemory,
            cpuExceeded: cpuExceeded,
            memoryExceeded: memoryExceeded
        ]
    }

    @Whitelisted
    def performSecurityScan() {
        def properties = build.getProject().getProperties()
        def securityScanEnabled = properties.get('khulnasoft.security.scan.enabled', 'true').toBoolean()
        def vulnerabilityThreshold = properties.get('build.security.vulnerability.threshold', 'high')

        if (securityScanEnabled) {
            LOG.info("Performing security scan for build ${build.getNumber()} with threshold: ${vulnerabilityThreshold}")
            // Placeholder: Integrate with security scanning tools (e.g., OWASP Dependency-Check)
            def scanResults = [
                vulnerabilitiesFound: 2,  // Example: number of vulnerabilities
                threshold: vulnerabilityThreshold,
                passed: true  // Example: scan passed if under threshold
            ]

            if (!scanResults.passed) {
                def message = "Security scan failed: ${scanResults.vulnerabilitiesFound} vulnerabilities exceed threshold"
                sendNotification(message, "slack")
            }

            return scanResults
        } else {
            LOG.info("Security scan disabled for build ${build.getNumber()}")
            return [enabled: false]
        }
    @Whitelisted
    def sendNotification(String message, String channel = null) {
        def properties = build.getProject().getProperties()
        def notificationsEnabled = properties.get('khulnasoft.notifications.enabled', 'true').toBoolean()
        def defaultChannels = properties.get('khulnasoft.notification.channels', 'email')

        if (notificationsEnabled) {
            def channels = channel ?: defaultChannels
            LOG.info("Sending notification for build ${build.getNumber()}: ${message} via ${channels}")
            // Example: Integrate with notification plugins or external services
            def notificationEvent = getBuildEvent() + [
                message: message,
                channels: channels,
                timestamp: System.currentTimeMillis()
            ]
            String sourceName = KhulnasoftJenkinsInstallation.get().getMetadataSource("notifications")
            send(notificationEvent, sourceName)
        } else {
            LOG.info("Notifications disabled for build ${build.getNumber()}")
        }
    }

    @Whitelisted
    def sendBuildMetrics() {
        def buildEvent = getBuildEvent()
        def metrics = [:]
        def properties = build.getProject().getProperties()

        // Collect basic metrics
        metrics["build_duration_ms"] = build.getDuration()
        metrics["build_result"] = build.getResult()?.toString()
        metrics["build_timestamp"] = build.getTimeInMillis()
        metrics["queue_wait_time_ms"] = build.getQueueId() != null ? System.currentTimeMillis() - build.getTimeInMillis() : 0

        // Check and use DevOps properties
        def metricsEnabled = properties.get('khulnasoft.devops.metrics.collection', 'true').toBoolean()
        def parallelExecution = properties.get('build.parallel.execution', 'true').toBoolean()
        def maxParallelJobs = properties.get('build.max.parallel.jobs', '4').toInteger()
        def optimizationEnabled = properties.get('build.optimization.enabled', 'true').toBoolean()
        def cacheEnabled = properties.get('build.cache.enabled', 'true').toBoolean()
        def cacheStrategy = properties.get('build.cache.strategy', 'incremental')
        def maxCacheSize = properties.get('build.cache.max.size', '1GB')
        def notificationsEnabled = properties.get('khulnasoft.notifications.enabled', 'true').toBoolean()
        def notificationChannels = properties.get('khulnasoft.notification.channels', 'email')
        def securityScanEnabled = properties.get('khulnasoft.security.scan.enabled', 'true').toBoolean()
        def vulnerabilityThreshold = properties.get('build.security.vulnerability.threshold', 'high')
        def cpuThreshold = properties.get('build.performance.cpu.threshold', '80').toInteger()
        def memoryThreshold = properties.get('build.performance.memory.threshold', '70').toInteger()

        // Add property-based metrics
        metrics["metrics_collection_enabled"] = metricsEnabled
        metrics["parallel_execution_enabled"] = parallelExecution
        metrics["max_parallel_jobs"] = maxParallelJobs
        metrics["optimization_enabled"] = optimizationEnabled
        metrics["cache_enabled"] = cacheEnabled
        metrics["cache_strategy"] = cacheStrategy
        metrics["max_cache_size"] = maxCacheSize
        metrics["notifications_enabled"] = notificationsEnabled
        metrics["notification_channels"] = notificationChannels
        metrics["security_scan_enabled"] = securityScanEnabled
        metrics["vulnerability_threshold"] = vulnerabilityThreshold
        metrics["cpu_threshold"] = cpuThreshold
        metrics["memory_threshold"] = memoryThreshold

        // Conditionally send metrics only if enabled
        if (metricsEnabled) {
            // Add to event
            Map event = buildEvent + ["metrics": metrics]
            String sourceName = KhulnasoftJenkinsInstallation.get().getMetadataSource("build_metrics")

            LOG.info("Sending enhanced build metrics for build ${build.getNumber()} with cache strategy: ${cacheStrategy}")
            send(event, sourceName)
        } else {
            LOG.info("Metrics collection disabled for build ${build.getNumber()}")
        }
    }

}