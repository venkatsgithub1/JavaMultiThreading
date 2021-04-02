package com.java.multithreading.hws.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UnsynchronizedCounter {
    static class Counter {
        int count;

        void inc() {
            count = count + 1;
        }

        int getCount() {
            return count;
        }
    }

    static Counter counter;
    static int times;

    public static void main(String[] args) throws IOException {
        int threadCount;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Number of threads:");
            threadCount = Integer.parseInt(br.readLine());
            System.out.println("Number of times to increment:");
            times = Integer.parseInt(br.readLine());
            while (threadCount <= 0) {
                System.out.println("Thread count should be greater than 0: try entering a positive number again:");
                threadCount = Integer.parseInt(br.readLine());
            }

            while (times <= 0) {
                System.out.println("Times increment should be greater than 0: try entering a positive number again:");
                times = Integer.parseInt(br.readLine());
            }

            counter = new Counter();

            List<IncrementerThread> threadsList = new ArrayList<>();
            IntStream.range(0, threadCount).forEach(x -> threadsList.add(new IncrementerThread()));
            IntStream.range(0, threadCount).forEach(x -> threadsList.get(x).start());
            IntStream.range(0, threadCount).forEach(x -> {
                try {
                    threadsList.get(x).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Actual value of increments should be: ".concat((threadCount * times) + ""));
            System.out.println("Final value after computation: ".concat(counter.getCount() + ""));

        }
    }

    static class IncrementerThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                counter.inc();
            }
        }
    }
}
