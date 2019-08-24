package demo.java.parallel_programming.experiments;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Assume there is a family with 5 siblings,
each sibling can use the toy scooter for a maximum of 5 seconds
5 siblings each try to get a lock, if they cannot acquire a lock, they wait.
 */

class SiblingScooterFight extends Thread {
    private int currentUsersCount = 0;
    private static Lock mutex = new ReentrantLock();
    private Boolean played = false;

    SiblingScooterFight(String name) {
        this.setName(name);
    }

    public void run() {
        while (currentUsersCount <= 5) {
            if (!played && mutex.tryLock()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is playing with scooter");
                    int randomPlayTime = new Random().nextInt(5);
                    Thread.sleep(randomPlayTime * 1000);
                    System.out.println(Thread.currentThread().getName() + " played for " + randomPlayTime + " seconds");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    currentUsersCount++;
                    played = true;
                    mutex.unlock();
                }
            } else {
                int randomWaitTime = new Random().nextInt(5);
                try {
                    Thread.sleep(randomWaitTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class TryLockExperiment {
    public static void main(String[] args) throws InterruptedException {
        SiblingScooterFight sibling1 = new SiblingScooterFight("Yudhisthira");
        SiblingScooterFight sibling2 = new SiblingScooterFight("Bheema");
        SiblingScooterFight sibling3 = new SiblingScooterFight("Arjuna");
        SiblingScooterFight sibling4 = new SiblingScooterFight("Nakula");
        SiblingScooterFight sibling5 = new SiblingScooterFight("Sahadeva");

        long playingStartTime = System.currentTimeMillis();
        sibling1.start();
        sibling2.start();
        sibling3.start();
        sibling4.start();
        sibling5.start();

        sibling1.join();
        sibling2.join();
        sibling3.join();
        sibling4.join();
        sibling5.join();

        System.out.println("siblings played for a total of " + ((System.currentTimeMillis() - playingStartTime) / 1000) + " seconds ");

    }
}
