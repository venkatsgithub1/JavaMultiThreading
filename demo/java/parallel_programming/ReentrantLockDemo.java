package demo.java.parallel_programming;

import java.util.concurrent.locks.ReentrantLock;

class ShopperForRenLockExampleClass extends Thread {
    static int garlicCount, potatoCount = 0;
    /*
    pencil is a lock object, specifically reentrant lock.
    A lock which can be incremented any number of times, but has to be unlocked
    those many number of times too.
     */
    static ReentrantLock pencil = new ReentrantLock();

    private void addGarlic() {
        pencil.lock();
        // Printing the hold count, i.e. number of times the pencil
        // object is locked.
        System.out.println("hold count:" + pencil.getHoldCount());
        garlicCount++;
        pencil.unlock();
    }

    private void addPotato() {
        // .lock issues lock.
        pencil.lock();
        potatoCount++;
        addGarlic();
        // .unlock issues unlock.
        pencil.unlock();
    }

    public void run() {
        for (int i = 0; i < 10_000; i++) {
            addGarlic();
            addPotato();
        }
    }
}

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new ShopperForRenLockExampleClass();
        Thread chef2 = new ShopperForRenLockExampleClass();

        chef1.start();
        chef2.start();
        chef1.join();
        chef2.join();

        System.out.println("Garlic count: " + ShopperForRenLockExampleClass.garlicCount + " is needed");
        System.out.println("Potatoes count: " + ShopperForRenLockExampleClass.potatoCount + " is needed");
    }
}
