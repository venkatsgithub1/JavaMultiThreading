package com.java.practice;

public class ThreeThreadsPrinter {
    public static void main(String[] args) {
        final Object lock = new Object();
        Thread t1 = new Thread(new Printer(1, 21, lock));
        Thread t2 = new Thread(new Printer(2, 21, lock));
        Thread t3 = new Thread(new Printer(0, 21, lock));

        t1.start();
        t2.start();
        t3.start();
    }
}

class Printer implements Runnable {

    private final int rem;
    private static int counter = 1;
    private final int UP_TO;
    private final Object lock;

    Printer(int rem, int upto, Object lock) {
        this.rem = rem;
        this.UP_TO = upto;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (counter < UP_TO - 1) {
            synchronized (lock) {
                if (counter % 3 != rem) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(counter + " and notify all called " + Thread.currentThread().getName());
                counter++;
                lock.notifyAll();

            }
        }
    }
}