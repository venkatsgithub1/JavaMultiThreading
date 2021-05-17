package com.java.practice;

public class ThreeThreadsPrinterWithCommunication {

    static final int LIMIT = 16;

    public static void main(String[] args) {

        Shared shared1 = new Shared(1);
        Shared shared2 = new Shared(2);
        Shared shared3 = new Shared(3);

        Thread thread1 = new Thread(shared1, "t1");
        Thread thread2 = new Thread(shared2, "t2");
        Thread thread3 = new Thread(shared3, "t3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class Shared implements Runnable {

    static final Object lock = new Object();
    static int sharedCounter = 1;
    final int divisor;

    public Shared(int divisor) {
        this.divisor = divisor;
    }

    @Override
    public void run() {
        while (sharedCounter <= ThreeThreadsPrinterWithCommunication.LIMIT)
            try {
                synchronized (lock) {
                    if (sharedCounter % divisor == 0) {
                        System.out.println("printing " + sharedCounter
                                + " from " + Thread.currentThread().getName());
                        sharedCounter++;
                    }

                    lock.notifyAll();
                    if (sharedCounter >= ThreeThreadsPrinterWithCommunication.LIMIT) {
                        continue;
                    }
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
