package com.java.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintEvenOddUsingLocks {
    private static final Lock lock = new ReentrantLock();
    private static final Condition evenCondition = lock.newCondition();
    private static final Condition oddCondition = lock.newCondition();
    private static int counter = 1;
    private static final int LIMIT = 100;

    public static void main(String[] args) {
        Thread oddThread = new Thread(() -> {
            while (counter <= LIMIT) {
                try {
                    // acquire lock.
                    lock.lock();
                    // print message.
                    System.out.println(Thread.currentThread().getName() + " " + counter++);
                    // tell evenCondition, that it can function now.
                    evenCondition.signal();
                    if (counter <= LIMIT) {
                        try {
                            // tell oddCondition to wait until it gets a signal.
                            oddCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread evenThread = new Thread(() -> {
            while (counter <= LIMIT) {
                try {
                    // acquire lock.
                    lock.lock();
                    // print message.
                    System.out.println(Thread.currentThread().getName() + " " + counter++);
                    // tell oddCondition, that it can function now.
                    oddCondition.signal();
                    if (counter <= LIMIT) {
                        try {
                            // tell evenCondition to wait until it gets a signal.
                            evenCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        });

        oddThread.start();
        evenThread.start();
    }
}
