package com.java.multithreading;

import java.util.Random;

public class InterruptsDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                int sleepTime = new Random().nextInt(6000);
                System.out.println("Sleeping for " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted after 3 secs");
                e.printStackTrace();
            }
        });

        t1.start();

        Thread.sleep(3000);
        t1.interrupt();
    }
}
