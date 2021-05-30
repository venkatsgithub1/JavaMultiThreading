package com.java.practice;

public class May302021PrintEvenOdd {
    static int initial = 1;

    public static void main(String[] args) {
        final int LIMIT = 20;
        final Object lock = new Object();
        Runnable evenRunnable = () -> {
            while (true) {
                synchronized (lock) {
                    if (initial % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + " " + initial);
                        initial++;
                        lock.notifyAll();
                        try {
                            if (initial > LIMIT) {
                                break;
                            }
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Runnable oddRunnable = () -> {
            while (initial <= LIMIT) {
                synchronized (lock) {
                    if (initial % 2 != 0) {
                        System.out.println(Thread.currentThread().getName() + " " + initial);
                        initial++;
                        lock.notifyAll();
                        try {
                            if (initial > LIMIT) {
                                break;
                            }
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread thread1 = new Thread(oddRunnable, "Odd Thread");
        Thread thread2 = new Thread(evenRunnable, "Even Thread");

        thread1.start();
        thread2.start();
    }
}
