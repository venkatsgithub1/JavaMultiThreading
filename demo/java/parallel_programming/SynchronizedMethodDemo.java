package demo.java.parallel_programming;

class ShopperForSyncExampleClass extends Thread {
    static int garlicCount = 0;

    /*
    synchronized keyword makes sure that lock for
    ShopperForSyncExampleClass is available with
    only one thread and other thread who wants to execute
    addGarlic needs to wait for the thread holding lock
    of the class object needs to finish.

    The method is static since, only one instance of the
    method is available for threads executing. If the method is per instance
    the locks on objects differ, because they are two separate objects and hence
    we face data race again.
     */
    public static synchronized void addGarlic() {
        garlicCount++;
    }

    public void run() {
        for (int i = 0; i < 10_000_000; i++) {
            addGarlic();
        }
    }
}

public class SynchronizedMethodDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new ShopperForSyncExampleClass();
        Thread chef2 = new ShopperForSyncExampleClass();

        chef1.start();
        chef2.start();
        chef1.join();
        chef2.join();

        System.out.println("Garlic count: " + ShopperForSyncExampleClass.garlicCount + " is needed");
    }
}
