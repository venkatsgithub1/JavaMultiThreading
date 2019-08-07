package demo.java.parallel_programming;

class KitchenCleaner extends Thread {
    public void run() {
        while(true) {
            System.out.println("cleaner cleaning the kitchen");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class DaemonThreadsDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread cleanerThread = new KitchenCleaner();
        cleanerThread.setDaemon(true);
        cleanerThread.start();

        System.out.println("Chef is cooking");
        Thread.sleep(600);
        System.out.println("Chef is cooking");
        Thread.sleep(600);
        System.out.println("Chef is cooking");
        Thread.sleep(600);
        System.out.println("Chef is done with cooking");
    }
}
