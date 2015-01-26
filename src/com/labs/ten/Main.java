package com.labs.ten;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 */
public class Main {
    // Simulation control
    public static Timer timer = new Timer();
    public static boolean running = true;
    public static Random random = new Random();

    // Simulation variables
    public static ExecutorService pool;
    public static int producerCount = 10;
    public static int consumerCount = 10;
    public static DragonBabyNamer[] producers = new DragonBabyNamer[producerCount];
    public static DragonParents[] consumers = new DragonParents[consumerCount];
    public static DragonBirthRegister queue;

    public static void main(String[] args) {
        // Create the FIFO queue
        queue = new DragonBirthRegister();

        // Create a thread pool
        pool = Executors.newFixedThreadPool(4);

        // Create the producer and consumer threads
        // See Note 1 below.
        for(int i = 0; i < producerCount; i++) producers[i] = new DragonBabyNamer(i, queue);
        for(int i = 0; i < consumerCount; i++) consumers[i] = new DragonParents(i, queue);

        System.out.println("Starting simulation.");
        System.out.println("Legend: <- consumes, -> produces, [--] queue, * item in queue, - free space in queue, [consumer|producer:<id>:<thread id>]");

        int iterations = 0; // Keep count of the iterations
        while(running && ++iterations > 0) {
            System.out.println(String.format("-- Iteration #%d --", iterations));

            // Execute the producer and consumer threads randomly
            pool.submit(producers[random.nextInt(producers.length)]);

            // Delay the consumer until the producer adds some content
            if(iterations > 5)
                pool.submit(consumers[random.nextInt(consumers.length)]);

            // Delay each iteration
            // See Note 2
            try { Thread.sleep(200); } catch(InterruptedException e) {}

            // Stop the simulation after 25 iterations
            if(iterations > 25) {
                System.out.println("Simulation over.");
                running = false;
            }
        }

        System.out.println("Shutting down thread pool. Some scheduled task may continue to execute hereafter.");

        // And empty the pool thereafter
        pool.shutdown();

        // Note 1:
        // Having multiple different instances of consumer/producer classes is redundant since they're each
        // functionally identical in this example but I did it anyway, for show.

        // Note 2:
        // Not delaying the execution of the loop is a memory leak
        // since the `submit` schedules the task until a thread is
        // available and sticks it into some collection. The producer
        // and consumer threads in this example sleep for X amount
        // of seconds to simulate actually doing something which
        // leads to all the threads being blocked at some point,
        // it's then that the thread pool schedules the tasks into
        // some collection for each loop iteration and causes
        // large amounts of memory to be used up.

        // Note 3:
        // The output looks like this:
        //
        //      -- Iteration #8 --
        //      [producer:#3] -> Draigoch
        //      [queue] [****------------------------------------]
        //      [consumer:#4] <- Elliott
        //      [queue] [*****-----------------------------------]
        //      -- Iteration #9 --
        //      [producer:#9] -> Dulcy
        //      [queue] [****------------------------------------]
        //      [consumer:#1] <- Mizunoeryu
        //      [queue] [*****-----------------------------------]
        //
        // It looks like it's adding and taking away with the loop block
        // but it's actually from separate threads, they just finish
        // their execution before the loop iterates onto the next cycle.
        // You can denote different threads from [<thread type>:#<thread id>]
        //
        // This shows the thread locking by the linear sequence. Each
        // task is scheduled to be completed at the same time, yet the
        // producer always gets in first because it locks the queue from
        // the consumer. It waits until is is unlocked before it can consume.
        //
        // If we change the thread pool count to 4 (from ten) (current):
        // Towards the end, some strings build up in the queue since we
        // only allow four threads executing simultaneously and their
        // tasks take long than each iteration.
    }
}
