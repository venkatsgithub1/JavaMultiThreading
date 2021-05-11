package com.java.practice;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Here thread communication is not required, hence wait and notify
 * doesn't have significance, thread 1 acquires lock first and executes
 * its code and once lock is release, thread 2 acquires lock and executes.
 */
public class ThreadingWithoutWaitAndNotify {
    static int counter = 0;

    public static void main(String[] args) {
        Object lock = new Object();
        Runnable runnable = () -> {
            ThreadLocalRandom x = ThreadLocalRandom.current();
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    counter++;
                    try {
                        Thread.sleep(x.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " " + counter);
                }
            }
        };

        Thread t1 = new Thread(runnable, "Thread 1");
        Thread t2 = new Thread(runnable, "Thread 2");

        t1.start();
        t2.start();
    }
}
