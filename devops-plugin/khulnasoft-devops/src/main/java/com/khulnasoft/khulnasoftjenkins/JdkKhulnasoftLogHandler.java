package com.khulnasoft.khulnasoftjenkins;

import com.khulnasoft.khulnasoftjenkins.model.EventRecord;
import com.khulnasoft.khulnasoftjenkins.model.EventType;
import com.khulnasoft.khulnasoftjenkins.utils.LogConsumer;
import com.khulnasoft.khulnasoftjenkins.utils.KhulnasoftLogService;
import hudson.model.Computer;
import jenkins.model.Jenkins;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.*;
import java.util.logging.Formatter;

import static com.khulnasoft.khulnasoftjenkins.Constants.JDK_FINE_LOG_BATCH;


public class JdkKhulnasoftLogHandler extends Handler {
    private Lock maintenanceLock = new ReentrantLock();

    private List<EventRecord> verboseLogCache = Collections.synchronizedList(new ArrayList(JDK_FINE_LOG_BATCH));
    private Level filterLevel = Level.parse(System.getProperty(JdkKhulnasoftLogHandler.class.getName() + ".level", "INFO"));
    private LogEventFormatter khulnasoftFormatter;

    public JdkKhulnasoftLogHandler() {
        this.khulnasoftFormatter = new LogEventFormatter();
        setFilter(new LogFilter());
        setLevel(filterLevel);
    }

    @Override
    public void publish(LogRecord record) {
        if (!KhulnasoftJenkinsInstallation.isLogHandlerRegistered()) {
            return;
        }
        if (!isLoggable(record)) {
            return;
        }
        // jdk logger shares metadata config of console log
        if (KhulnasoftJenkinsInstallation.get().isEventDisabled(EventType.CONSOLE_LOG)) {
            return;
        }
        Map logEvent = khulnasoftFormatter.getEvent(record);
        if (logEvent == null || logEvent.isEmpty()) {
            return;
        }
        EventRecord logEventRecord = new EventRecord(logEvent, EventType.LOG);
        logEventRecord.setSource("logger://" + record.getLoggerName());
        if (record.getLevel().intValue() < Level.INFO.intValue()) {
            verboseLogCache.add(logEventRecord);
            if (verboseLogCache.size() >= JDK_FINE_LOG_BATCH) {
                flush();
            }
        } else {
            KhulnasoftLogService.getInstance().send(logEventRecord);
        }

    }

    @Override
    public void flush() {
        if (this.verboseLogCache.isEmpty()) {
            return;
        }
        List copyList = new ArrayList();
        try {
            maintenanceLock.lock();
            copyList.addAll(verboseLogCache);
            verboseLogCache.clear();
        } finally {
            maintenanceLock.unlock();
        }
        KhulnasoftLogService.getInstance().sendBatch(copyList, EventType.LOG);
    }

    @Override
    public void close() throws SecurityException {

    }

    private static class LogFilter implements Filter {
        //logger may trigger recursive call, need skip them
        private final String[] skipLoggerNames = {
                KhulnasoftLogService.class.getName(), LogConsumer.class.getName(),
                "jenkins.InitReactorRunner", "hudson.util.BootFailure",
                "shaded.splk.org.apache.http", "hudson.node_monitors", "hudson.Extension"};

        @Override
        public boolean isLoggable(LogRecord record) {
            String logSource = record.getSourceClassName();
            String loggerName = record.getLoggerName();
            if (logSource == null || loggerName == null) {
                return false;
            }
            for (int i = 0; i < skipLoggerNames.length; i++) {
                String skipPrefix = skipLoggerNames[i];
                if (logSource.startsWith(skipPrefix) || loggerName.startsWith(skipPrefix)) {
                    return false;
                }
            }
            if (record.getThrown() != null) {
                StackTraceElement[] cause = record.getThrown().getStackTrace();
                for (StackTraceElement element : cause) {
                    if (element.getClassName().equals(JdkKhulnasoftLogHandler.class.getName())) {
                        KhulnasoftLogService.LOG.log(Level.SEVERE, "discard recursive log\n{0}", record.getMessage());
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static class LogEventFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return formatMessage(record);
        }

        public Map getEvent(LogRecord record) {
            Map event = new HashMap<>();
            event.put("thread_id", record.getThreadID());
            event.put("level", record.getLevel().getName());
            //event.put("level_int", record.getLevel().intValue());
            event.put("message", formatMessage(record));
            String source;
            if (record.getSourceClassName() != null) {
                source = record.getSourceClassName();
                if (record.getSourceMethodName() != null) {
                    source += " " + record.getSourceMethodName();
                }
            } else {
                source = record.getLoggerName();
            }
            event.put("log_source", source);
            if (record.getLevel().intValue() > Level.INFO.intValue() && record.getThrown() != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                String logStackTrace = sw.toString();
                event.put("log_thrown", logStackTrace);
            }
            return event;
        }
    }

    public static final class LogHolder {
        /**
         * This field is used on each slave node to record log records on the slave.
         */
        static final JdkKhulnasoftLogHandler LOG_HANDLER = new JdkKhulnasoftLogHandler();

        public static void getSlaveLog(Computer computer) {
            if (computer == null || computer instanceof Jenkins.MasterComputer) {
                return;
            }
            try {
                List<LogRecord> records = computer.getLogRecords();
                for (LogRecord record : records) {
                    LOG_HANDLER.publish(record);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}