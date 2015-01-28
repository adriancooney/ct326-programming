package com.labs.ten;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 *
 * # Question
 *
 * Part 1:
 * Implement a FIFO queue up to some bounded maximum size to hold String objects that can
 * be accessed by multiple Producer and Consumer threads. The Producer threads produce String
 * objects while the Consumer threads consume these objects. Every String object produced must
 * be consumed exactly once.  Use the ReentrantLock class and Condition variables in your
 * implementation. Write an application class that create a random number of Producer and
 * Consumer threads at startup to test your implementation.
 *
 * Print out queue statistics e.g. number of items in the queue and
 * thread access details as the simulation progresses.
 *
 * Part 2:
 * Modify the code in Part 1, so that there is no bounds on the size of the stack (LIFO).
 * Tip: use a List instead of arrays.
 */
public class Main {
    public static enum Parts {
        ONE, TWO
    }

    // Edit to change which part of the question is running
    public static final Parts CURRENT_PART = Parts.TWO;

    // Simulation control
    public static boolean running = true;
    public static Random random = new Random();
    public static int max_size;

    // Simulation variables
    public static ExecutorService pool;
    public static int producerCount = 10;
    public static int consumerCount = 10;
    public static DragonBabyNamer[] producers = new DragonBabyNamer[producerCount];
    public static DragonParents[] consumers = new DragonParents[consumerCount];
    public static DragonBirthRegister queue;

    public static void main(String[] args) {
        if(CURRENT_PART == Parts.ONE)
            max_size = 3; // Set the queue size to be small to demonstrate being full and empty

        // Create the FIFO queue
        queue = new DragonBirthRegister(max_size);

        // Create a thread pool
        // Four is a good number of threads to demonstrate the blocking.
        pool = Executors.newFixedThreadPool(4);

        // Create the producer and consumer threads
        // See Note 1 below.
        for(int i = 0; i < producerCount; i++) producers[i] = new DragonBabyNamer(i, queue);
        for(int i = 0; i < consumerCount; i++) consumers[i] = new DragonParents(i, queue);

        System.out.println(String.format("Starting simulation for part %d. Production is faster than consumption. For each production, there is one consumption.", CURRENT_PART == Parts.ONE ? 1 : 2));
        System.out.println("Legend: [<thread name>], << consumes, >> produces, * item in queue, - free space in queue");

        int iterations = 0; // Keep count of the iterations
        while(running && ++iterations > 0) {
            // Pick and execute the producer and consumer threads randomly
            pool.submit(producers[random.nextInt(producers.length)]);
            pool.submit(consumers[random.nextInt(consumers.length)]);

            // Stop the simulation after 25 iterations
            if(iterations > 25) {
                System.out.println("Tasks pushed into queue.");
                running = false;
            }
        }

        System.out.println("Shutting down thread pool. Scheduled tasks will continue to execute hereafter.");

        // And empty the pool thereafter
        pool.shutdown();

        // Note 1:
        // Having multiple different instances of consumer/producer classes is redundant since they're each
        // functionally identical in this example but I did it anyway to demo.

        // Note 2:
        // I discovered a problem in my first attempt at a timed simulation.
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
        //    [*--] << "Ridley" [pool-1-thread-1]
        //    [**-] << "Drogon" [pool-1-thread-3]
        //    [***] << "Grandeeney" [pool-1-thread-1]
        //    [!] Queue full! Waiting on space. Postponing addition of 'Rhaegal' to queue on thread [pool-1-thread-1].
        //    [**-] >> "Ridley" [pool-1-thread-2]
        //    [+] Item in queue for thread [pool-1-thread-1].
        //    [***] << "Rhaegal" [pool-1-thread-1]
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
