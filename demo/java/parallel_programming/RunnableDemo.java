package demo.java.parallel_programming;

class SousChef2 implements Runnable {
    public void run() {
        System.out.println("chef started and waiting for the pre salad dressing to be ready");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Chef is done with the salad");
    }
}

public class RunnableDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main chef started and requesting sous chef for help");
        Thread sousChef = new Thread(new SousChef2());
        System.out.println(sousChef.getState());

        System.out.println("Chef requests Sous Chef to start");
        sousChef.start();
        System.out.println(sousChef.getState());

        System.out.println("Main chef working on pre salad dressing");
        Thread.sleep(500);
        System.out.println(sousChef.getState());

        System.out.println("Main chef waits for sous chef to finish up");
        sousChef.join();
        System.out.println(sousChef.getState());

        System.out.println("main chef and sous chef are done with cooking the salad");
    }
}
