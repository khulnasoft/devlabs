package com.khulnasoft.khulnasoftjenkins.model;

import hudson.model.TaskListener;
import hudson.triggers.Trigger;
import hudson.model.AbstractProject;
import jenkins.model.Jenkins;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for periodic work that runs asynchronously in the background.
 * This is a simplified version for the Khulnasoft plugin.
 */
public abstract class AsyncPeriodicWork extends Trigger<AbstractProject> {

    private static final Logger LOGGER = Logger.getLogger(AsyncPeriodicWork.class.getName());

    protected AsyncPeriodicWork(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            execute(TaskListener.NULL);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error executing periodic work: " + getName(), e);
        }
    }

    /**
     * Execute the periodic work
     * @param listener task listener for logging
     * @throws IOException if execution fails
     * @throws InterruptedException if execution is interrupted
     */
    protected abstract void execute(TaskListener listener) throws IOException, InterruptedException;

    /**
     * Get the recurrence period for this work
     * @return period in milliseconds
     */
    public abstract long getRecurrencePeriod();

    @Override
    public TriggerDescriptor getDescriptor() {
        return new TriggerDescriptor() {
            @Override
            public String getDisplayName() {
                return "Async Periodic Work";
            }
        };
    }

    /**
     * Simplified TriggerDescriptor for this use case
     */
    private static class TriggerDescriptor extends hudson.triggers.TriggerDescriptor {
        @Override
        public String getDisplayName() {
            return "Async Periodic Work";
        }
    }
}
