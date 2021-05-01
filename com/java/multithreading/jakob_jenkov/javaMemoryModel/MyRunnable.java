package com.java.multithreading.jakob_jenkov.javaMemoryModel;

public class MyRunnable implements Runnable {
    private int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 1_000_000; i++) {
            // fix for incorrect counts, when two threads share a same
            // runnable, where count is common for both threads.
            synchronized (this) {
                this.count++;
            }
        }
        System.out.println(Thread.currentThread().getName() + " count:" + this.count);
    }
}
