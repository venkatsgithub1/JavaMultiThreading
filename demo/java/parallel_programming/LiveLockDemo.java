package demo.java.parallel_programming;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher4LiveLockDemo extends Thread {
    private Lock firstChopStick, secondChopStick;
    private static int sushiCount = 500_000;
    // this random variable will let the current thread sleep for random time
    // when releasing lock so that live lock doesn't occur.
    private Random rps = new Random();

    Philosopher4LiveLockDemo(String name, Lock firstChopStick, Lock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        // local variable to the method

        while (sushiCount > 0) {
            firstChopStick.lock();
            if (!secondChopStick.tryLock()) {
                System.out.println(this.getName() + " releasing lock on first chopstick ");
                firstChopStick.unlock();
                try {
                    Thread.sleep(rps.nextInt(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (sushiCount > 0) {
                        sushiCount--;
                        System.out.println(this.getName() + " took a piece, sushi rem: " + sushiCount);
                    }
                } finally {
                    secondChopStick.unlock();
                    firstChopStick.unlock();
                }
            }
        }
    }
}

public class LiveLockDemo {
    public static void main(String[] args) {
        Lock chopStick1 = new ReentrantLock();
        Lock chopStick2 = new ReentrantLock();
        Lock chopStick3 = new ReentrantLock();

        new Philosopher4LiveLockDemo("Socrates", chopStick1, chopStick2).start();
        new Philosopher4LiveLockDemo("Plato", chopStick2, chopStick3).start();
        new Philosopher4LiveLockDemo("Aristotle", chopStick3, chopStick1).start();
    }
}
