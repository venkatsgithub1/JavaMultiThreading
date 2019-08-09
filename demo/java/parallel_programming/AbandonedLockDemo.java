package demo.java.parallel_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PhilosopherForAbLockDemo extends Thread {
    private Lock firstChopStick, secondChopStick;
    private static int sushiCount = 500;

    PhilosopherForAbLockDemo(String name, Lock firstChopStick, Lock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        while (sushiCount > 0) {
            firstChopStick.lock();
            secondChopStick.lock();

            try {
                if (sushiCount > 0) {
                    sushiCount--;
                    System.out.println(this.getName() + " took a piece, sushi rem: " + sushiCount);
                }

                // simulating an unhandled exception
                if (sushiCount == 10) {
                    System.out.println(1 / 0);
                }
            } finally {
                secondChopStick.unlock();
                firstChopStick.unlock();
            }
        }
    }
}

/*
Sometimes when one thread already has lock on one resource,
another thread requests for the same resource, it needs to
wait, instead of waiting, the later thread can abandon the
request for lock after sometime (timeout) and do its work and comeback
for the locking the resource later
 */
public class AbandonedLockDemo {
    public static void main(String[] args) {
        Lock chopStick1 = new ReentrantLock();
        Lock chopStick2 = new ReentrantLock();
        Lock chopStick3 = new ReentrantLock();

        new PhilosopherForAbLockDemo("Socrates", chopStick1, chopStick2).start();
        new PhilosopherForAbLockDemo("Plato", chopStick2, chopStick3).start();
        new PhilosopherForAbLockDemo("Aristotle", chopStick1, chopStick3).start();
    }
}
