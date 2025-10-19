package com.khulnasoft.khulnasoftjenkins.utils;

import com.khulnasoft.khulnasoftjenkins.model.EventRecord;
import com.khulnasoft.khulnasoftjenkins.model.EventType;
import com.khulnasoft.khulnasoftjenkins.KhulnasoftJenkinsInstallation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for sending log events to Khulnasoft
 */
public class KhulnasoftLogService {

    private static final Logger LOG = Logger.getLogger(KhulnasoftLogService.class.getName());
    private static KhulnasoftLogService instance;

    private final BlockingQueue<Object> eventQueue = new LinkedBlockingQueue<>(10000);
    private final AtomicBoolean workerRunning = new AtomicBoolean(false);
    private final AtomicBoolean workerStopped = new AtomicBoolean(false);

    /**
     * Get singleton instance
     * @return the service instance
     */
    public static synchronized KhulnasoftLogService getInstance() {
        if (instance == null) {
            instance = new KhulnasoftLogService();
        }
        return instance;
    }

    private KhulnasoftLogService() {
        startWorker();
    }

    /**
     * Send an event to Khulnasoft
     * @param data the event data
     * @param eventType the event type
     * @return true if queued successfully
     */
    public boolean send(Map<String, Object> data, EventType eventType) {
        return send(data, eventType, null);
    }

    /**
     * Send an event to Khulnasoft with source
     * @param data the event data
     * @param eventType the event type
     * @param source the source name
     * @return true if queued successfully
     */
    public boolean send(Map<String, Object> data, EventType eventType, String source) {
        if (!KhulnasoftJenkinsInstallation.get().isEnabled()) {
            return false;
        }

        EventRecord record = new EventRecord(data, eventType);
        if (source != null) {
            record.setSource(source);
        }

        return send(record);
    }

    /**
     * Send an event record to Khulnasoft
     * @param record the event record
     * @return true if queued successfully
     */
    public boolean send(EventRecord record) {
        if (!KhulnasoftJenkinsInstallation.get().isEnabled()) {
            return false;
        }

        try {
            boolean offered = eventQueue.offer(record);
            if (!offered) {
                LOG.log(Level.WARNING, "Event queue full, dropping event: " + record);
            }
            return offered;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error queuing event", e);
            return false;
        }
    }

    /**
     * Send a string event to Khulnasoft
     * @param data the string data
     * @param eventType the event type
     * @return true if queued successfully
     */
    public boolean send(String data, EventType eventType) {
        return send(data, eventType, null);
    }

    /**
     * Send a string event to Khulnasoft with source
     * @param data the string data
     * @param eventType the event type
     * @param source the source name
     * @return true if queued successfully
     */
    public boolean send(String data, EventType eventType, String source) {
        EventRecord record = new EventRecord(data, eventType);
        if (source != null) {
            record.setSource(source);
        }
        return send(record);
    }

    /**
     * Send a batch of events
     * @param events list of events
     * @param eventType the event type for all events
     * @return true if all queued successfully
     */
    public boolean sendBatch(List<?> events, EventType eventType) {
        boolean allSent = true;
        for (Object event : events) {
            if (event instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> eventData = (Map<String, Object>) event;
                if (!send(eventData, eventType)) {
                    allSent = false;
                }
            } else if (event instanceof EventRecord) {
                if (!send((EventRecord) event)) {
                    allSent = false;
                }
            } else {
                // Convert to string
                if (!send(event.toString(), eventType)) {
                    allSent = false;
                }
            }
        }
        return allSent;
    }

    /**
     * Start the worker thread
     */
    private void startWorker() {
        if (workerRunning.compareAndSet(false, true)) {
            Thread workerThread = new Thread(() -> {
                while (!workerStopped.get()) {
                    try {
                        Object event = eventQueue.take();

                        if (event instanceof EventRecord) {
                            processEvent((EventRecord) event);
                        } else if (event instanceof Map) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> eventData = (Map<String, Object>) event;
                            // This would typically send to HTTP endpoint
                            LOG.fine("Would send event to Khulnasoft: " + eventData);
                        } else {
                            LOG.fine("Would send event to Khulnasoft: " + event);
                        }

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Error processing event", e);
                    }
                }
            }, "Khulnasoft-Event-Worker");
            workerThread.setDaemon(true);
            workerThread.start();
        }
    }

    /**
     * Process an event record
     * @param record the event record
     */
    private void processEvent(EventRecord record) {
        try {
            // This is where the actual HTTP sending would happen
            // For now, just log the event
            LOG.fine("Processing event: " + record);

            // In a real implementation, this would:
            // 1. Format the event according to Khulnasoft HEC format
            // 2. Send via HTTP POST to the configured endpoint
            // 3. Handle retries and error conditions

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error processing event: " + record, e);
        }
    }

    /**
     * Stop the worker thread
     */
    public void stopWorker() {
        workerStopped.set(true);
        if (workerRunning.get()) {
            // Interrupt the worker thread if it's waiting
            // The thread will check workerStopped and exit
        }
    }

    /**
     * Release connections (placeholder)
     */
    public void releaseConnection() {
        // In a real implementation, this would close HTTP connections
        LOG.info("Releasing Khulnasoft connections");
    }

    /**
     * Get queue size for monitoring
     * @return current queue size
     */
    public int getQueueSize() {
        return eventQueue.size();
    }
}
