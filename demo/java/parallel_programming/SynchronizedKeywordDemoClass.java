package demo.java.parallel_programming;

class ShopperForSyncKeywordExampleClass extends Thread {
    static int garlicCount = 0;

    public void run() {
        for (int i = 0; i < 10_000_000; i++) {
            /*
            In the synchronized statement, we use class name
            since we want any objects of this class to wait
            for another write when current write is in progress
            new threads will have to wait for current thread
            to finish their processing on the data.
             */
            synchronized (ShopperForSyncKeywordExampleClass.class) {
                garlicCount++;
            }
        }
    }
}

public class SynchronizedKeywordDemoClass {
    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new ShopperForSyncKeywordExampleClass();
        Thread chef2 = new ShopperForSyncKeywordExampleClass();

        chef1.start();
        chef2.start();
        chef1.join();
        chef2.join();

        System.out.println("Garlic count: " + ShopperForSyncKeywordExampleClass.garlicCount + " is needed");
    }
}
