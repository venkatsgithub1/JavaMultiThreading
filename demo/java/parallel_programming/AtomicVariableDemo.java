package demo.java.parallel_programming;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicShopper extends Thread {
    /*
    AtomicInteger is a Thread safe way of adding integers
    to avoid data races that can occur on normal integers
     */
    static AtomicInteger garlicCount = new AtomicInteger(0);

    public void run() {
        for (int i = 0; i < 10_000_000; i++) {
            garlicCount.incrementAndGet();
        }
    }
}

public class AtomicVariableDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new AtomicShopper();
        Thread chef2 = new AtomicShopper();

        chef1.start();
        chef2.start();
        chef1.join();
        chef2.join();

        System.out.println("Garlic needed: " + AtomicShopper.garlicCount);
    }
}
