package com.java.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {
    public static void main(String[] args) {
        Runnable r1 = ()->{
            System.out.println(Thread.currentThread().getName());
        };

        // creating a thread pool
        ExecutorService service = Executors.newFixedThreadPool(10);

        /*
        Looping 11 times, a thread gets executed twice, since only ten
        threads are available on thread pool.
         */
        for(int i=0;i<11;i++) {
            service.execute(r1);
        }

        /*
        Shutting down.
         */
        service.shutdown();
    }
}
