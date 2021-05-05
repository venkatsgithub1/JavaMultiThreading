package com.java.practice;

public class PlayPingPongUsingSynchronized {


    public static void main(String[] args) {

        Object lock = new Object();

        Thread t1 = new Thread(new Ping(21, lock));
        Thread t2 = new Thread(new Pong(21, lock));

        t1.start();
        t2.start();
    }
}

class Ping implements Runnable {

    int counter = 1;
    final int limit;
    final Object lock;

    Ping(int till, Object lock) {
        limit = till;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Ping " + counter);
            counter++;
            synchronized (lock) {
                lock.notifyAll();
                try {
                    Thread.sleep(100);
                    if (counter > limit) {
                        break;
                    }
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Pong implements Runnable {
    int counter = 1;
    final int limit;
    final Object lock;

    Pong(int till, Object lock) {
        limit = till;
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            synchronized (lock) {
                System.out.println("Pong " + counter);
                counter++;
                lock.notifyAll();
                try {
                    Thread.sleep(100);
                    if (counter > limit) {
                        break;
                    }
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}