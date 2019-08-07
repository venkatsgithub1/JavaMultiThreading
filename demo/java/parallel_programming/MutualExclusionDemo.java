package demo.java.parallel_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShopperWithExclusion extends Thread {
    static int garlicCount = 0;
    /*
    Creating a lock on a shared object called pencil
    pencil is just a name, a lock's name can be anything.
    Since it makes sense to name it so here, hence the
    name is.

    Here we need to use Reentrant lock, one of various types of locks.
     */
    static Lock pencil = new ReentrantLock();

    public void run() {
        for (int i = 0; i < 5; i++) {
            /*
            we could've protected the entire for loop using lock
            but that will make the other thread wait for the for current thread's
            looping to complete. Instead we can use lock only on shared resource
            i.e. garlicCount, that reduces the time taken to execute the program
             */
            pencil.lock();
            garlicCount++;
            pencil.unlock();
            System.out.println(Thread.currentThread().getName() + " is thinking");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MutualExclusionDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new ShopperWithExclusion();
        Thread chef2 = new ShopperWithExclusion();

        chef1.start();
        chef2.start();
        chef1.join();
        chef2.join();

        System.out.println("Garlic count: " + ShopperWithExclusion.garlicCount + " is needed");
    }
}
