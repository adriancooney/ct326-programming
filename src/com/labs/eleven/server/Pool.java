package com.labs.eleven.server;

import com.labs.common.Logger;

import java.util.concurrent.*;

/**
 * Adrian Cooney (12394581)
 * 12/02/15 com.labs.eleven.server
 */
public class Pool extends ThreadPoolExecutor {
    private Logger logger = new Logger("Pool");

    /**
     * Create a new thread pool.
     */
    public Pool() {
        super(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

        logger.log("New pool created.");
    }

    /**
     * Log when a task has completed execution.
     * @param t
     * @param r
     */
    protected void beforeExecute(Thread t, Runnable r) {
        logger.log("Task started.");
        super.beforeExecute(t, r);
    }

    /**
     * Log when a task is completed.
     * @param r
     * @param t
     */
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        logger.log("Thread closed.");
        if (t != null) t.printStackTrace();
    }
}