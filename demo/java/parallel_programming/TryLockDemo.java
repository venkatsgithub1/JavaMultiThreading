package demo.java.parallel_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShopperForTryLock extends Thread {
    private int itemsToAdd = 0;
    private static int itemsOnNotepad = 0;
    private static Lock pencil = new ReentrantLock();

    ShopperForTryLock(String name) {
        this.setName(name);
    }

    public void run() {
        // if number of items on notepad is less than or equal to 20
        // go inside and check if items to add count is greater than zero and
        // try to acquire lock on pencil with try lock.
        while (itemsOnNotepad <= 20) {
            if ((itemsToAdd > 0) && pencil.tryLock()) {
                try {
                    itemsOnNotepad += itemsToAdd;
                    System.out.println(this.getName() + " added " + itemsToAdd + " item(s) to notepad.");
                    itemsToAdd = 0;
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    pencil.unlock();
                }
            } else {
                // else keep doing other work.
                try {
                    Thread.sleep(100);
                    itemsToAdd++;
                    System.out.println(this.getName() + " found something to buy.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class TryLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new ShopperForTryLock("Chef 1");
        Thread chef2 = new ShopperForTryLock("Chef 2");

        long start = System.currentTimeMillis();

        chef1.start();
        chef2.start();

        chef1.join();
        chef2.join();

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Elapsed time:" + (elapsed / 1000) + " seconds");
    }
}
