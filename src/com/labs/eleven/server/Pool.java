package com.labs.eleven.server;

import com.labs.common.Logger;

import java.util.concurrent.*;

/**
 * Adrian Cooney (12394581)
 * 12/02/15 com.labs.eleven.server
 */
public class Pool extends ThreadPoolExecutor {
    private Logger logger = new Logger("Pool");

    public Pool() {
        super(4, // core threads
                5, // max threads
                1, // timeout
                TimeUnit.MINUTES, // timeout units
                new LinkedBlockingQueue<Runnable>() // work queue
        );

        logger.log("New pool created.");
    }

    protected void terminated() {
        try {
            logger.log("Thread terminated.");
        } finally {
            super.terminated();
        }
    }

    protected void beforeExecute(Thread t, Runnable r) {
        logger.log("Thread created.");
        super.beforeExecute(t, r);
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        logger.log("Thread closed.");
        if (t != null) t.printStackTrace();
    }
}
