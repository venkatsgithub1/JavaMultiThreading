package com.java.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceWithLatch {
    public static void main(String[] args) throws InterruptedException {
        Runnable r1 = ()->{
            System.out.println(Thread.currentThread().getName());
        };

        Integer numberOfThreads = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        /*
        created count down latch with count 5
         */
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);

        /*
        5 threads of service will be used out of 10.
         */
        for(int i=0;i<numberOfThreads;i++) {
            executorService.submit(r1);
            /*
            after every service is over, count down decrements countdown latch
             */
            countDownLatch.countDown();
        }

        /*
        Used to stop main from exiting, acts as wait kind of thing
         */
        countDownLatch.await();

        /*
        Shuts down after all work is done.
         */
        executorService.shutdown();

        System.out.println("finished");
    }
}
