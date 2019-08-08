package demo.java.parallel_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShopperForRenLockExampleClass extends Thread {
    static int garlicCount, potatoCount = 0;
    static ReentrantLock pencil = new ReentrantLock();

    private void addGarlic() {
        pencil.lock();
        System.out.println("hold count:"+pencil.getHoldCount());
        garlicCount++;
        pencil.unlock();
    }

    private void addPotato() {
        pencil.lock();
        potatoCount++;
        addGarlic();
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
