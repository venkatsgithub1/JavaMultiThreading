package demo.java.parallel_programming;

class Shopper extends Thread {
    static int garlicCount = 0;

    public void run() {
        for(int i=0;i<10_000_000;i++) {
            garlicCount++;
        }
    }
}

public class DataRaceDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread chef1 = new Shopper();
        Thread chef2 = new Shopper();

        chef1.start();
        chef2.start();

        chef1.join();
        chef2.join();

        System.out.println("garlic needed: "+Shopper.garlicCount);
    }
}
