package demo.java.parallel_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher4StarveDemo extends Thread {
    private Lock firstChopStick, secondChopStick;
    private static int sushiCount = 500_000;

    Philosopher4StarveDemo(String name, Lock firstChopStick, Lock secondChopStick) {
        this.setName(name);
        this.firstChopStick = firstChopStick;
        this.secondChopStick = secondChopStick;
    }

    public void run() {
        // local variable to the method
        int sushiEaten = 0;

        while (sushiCount > 0) {
            firstChopStick.lock();
            secondChopStick.lock();

            try {
                if (sushiCount > 0) {
                    sushiCount--;
                    // every time a philosopher takes a sushi, sushiEaten is incremented.
                    sushiEaten++;
                    System.out.println(this.getName() + " took a piece, sushi rem: " + sushiCount);
                }
            } finally {
                secondChopStick.unlock();
                firstChopStick.unlock();
            }
        }

        System.out.println("Sushi taken by " + this.getName() + " is " + sushiEaten);
    }
}

public class StarvationDemo {
    public static void main(String[] args) {
        Lock chopStick1 = new ReentrantLock();
        Lock chopStick2 = new ReentrantLock();
//        Lock chopStick3 = new ReentrantLock();

        /*
        Creating 15000 threads competing over 500000 sushi.
        Some threads may not get a single sushi piece,
        because those threads didn't get a single chance to lock.
        These threads were starving for work load.
         */
        for (int i = 0; i < 5_000; i++) {
            new Philosopher4StarveDemo("Socrates-" + i, chopStick1, chopStick2).start();
            new Philosopher4StarveDemo("Plato-" + i, chopStick1, chopStick2).start();
            new Philosopher4StarveDemo("Aristotle-" + i, chopStick1, chopStick2).start();
        }
//        new Philosopher4StarveDemo("Plato", chopStick2, chopStick3).start();
//        new Philosopher4StarveDemo("Aristotle", chopStick1, chopStick3).start();
    }
}
