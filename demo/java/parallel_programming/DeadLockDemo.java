package demo.java.parallel_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {
    private Lock firstChopStick, secondChopStick;
    private static int sushiCount = 500_000;

    Philosopher(String name, Lock firstChopStick, Lock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        while (sushiCount > 0) {
            // locking both chop sticks.
            firstChopStick.lock();
            secondChopStick.lock();

            if (sushiCount > 0) {
                sushiCount--;
                System.out.println(this.getName() + " took a piece, remaining sushi: " + sushiCount);
            }

            // unlocking both chop sticks.
            firstChopStick.unlock();
            secondChopStick.unlock();
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        Lock chopStickA = new ReentrantLock();
        Lock chopStickB = new ReentrantLock();
        Lock chopStickC = new ReentrantLock();

        new Philosopher("Socrates", chopStickA, chopStickB).start();
        new Philosopher("Plato", chopStickB, chopStickC).start();
        new Philosopher("Aristotle", chopStickA, chopStickC).start();
    }
}
