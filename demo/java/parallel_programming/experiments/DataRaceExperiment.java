package demo.java.parallel_programming.experiments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
A class that implements runnable and has run method
to do thread's work.
The class doesn't have a mutex functionality.
 */
class Worker implements Runnable {

    static long count;

    @Override
    public void run() {
        for (int i = 0; i < 10_000; i++) {
            // below line has data race, we have to add below
            // statement between mutex lock and unlock.
            increment();
        }
    }

    private void increment() {
        count = count + 1;
    }
}

/*
A class that implements runnable and has run method
to do thread's work.
The class has a mutex functionality.
A modified and good version of Worker class.
 */
class WorkerWithoutDataRace implements Runnable {

    static long count;
    private static Lock mutex = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0; i < 10_000; i++) {
            // here we are placing increment between lock and unlock
            // so that only a thread having locked can do anything here
            // only after unlock method is called other threads can access
            mutex.lock();
            increment();
            mutex.unlock();
        }
    }

    private void increment() {
        count = count + 1;
    }
}

/*
Public class to test the functionality of data race.
 */
public class DataRaceExperiment {

    private static long countForLambdas;
    private static Lock mutex = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        runLambdaThreads();
        runNonLambdaThreads();
        runLambdaThreadsWithoutDataRace();
        runNonLambdaThreadsWithoutDataRace();
    }

    private static void runLambdaThreads() throws InterruptedException {

        countForLambdas = 0;
        System.out.println("Executing threading with lambdas");

        /*
        Creating a list of runnables to store lambdas.
         */
        List<Runnable> runnableList = new ArrayList<>();

        /*
        Looping 5 times and storing lambdas.
         */
        for (int i = 0; i < 5; i++) {
            runnableList.add(() -> {
                for (int j = 0; j < 10_000; j++) {
                    // below line has data race, we have to add below
                    // statement between mutex lock and unlock.
                    countForLambdas++;
                }
            });
        }

        /*
        Creating five threads
         */
        List<Thread> threads = new ArrayList<>();

        /*
        Creating 5 threads and adding them to a list.
         */
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(runnableList.get(i));
            threads.add(thread);
        }

        /*
        starting threads.
         */
        for (int i = 0; i < 5; i++) {
            Thread currentThread = threads.get(i);
            currentThread.start();
        }

        /*
        Issuing join on threads so that main thread waits
        for threads' execution to finish.
         */
        for (int i = 0; i < 5; i++) {
            Thread currentThread = threads.get(i);
            currentThread.join();
        }

        // printing worker count.
        System.out.println("count:" + countForLambdas);
    }

    private static void runLambdaThreadsWithoutDataRace() throws InterruptedException {

        countForLambdas = 0;
        System.out.println("Executing threading with lambdas (non data race version)");

        /*
        Creating a list of runnables to store lambdas.
         */
        List<Runnable> runnableList = new ArrayList<>();

        /*
        Looping 5 times and storing lambdas.
         */
        for (int i = 0; i < 5; i++) {
            runnableList.add(() -> {
                for (int j = 0; j < 10_000; j++) {
                    // below line has data race, added the increment between
                    // mutex lock and unlock so that thread that currently has lock
                    // has exclusive lock on the process, whereas other threads will have
                    // to wait to execute the line
                    mutex.lock();
                    countForLambdas++;
                    mutex.unlock();
                }
            });
        }

        /*
        Creating five threads
         */
        List<Thread> threads = new ArrayList<>();

        /*
        Creating 5 threads and adding them to a list.
         */
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(runnableList.get(i));
            threads.add(thread);
        }

        /*
        starting threads.
         */
        for (int i = 0; i < 5; i++) {
            Thread currentThread = threads.get(i);
            currentThread.start();
        }

        /*
        Issuing join on threads so that main thread waits
        for threads' execution to finish.
         */
        for (int i = 0; i < 5; i++) {
            Thread currentThread = threads.get(i);
            currentThread.join();
        }

        // printing worker count.
        System.out.println("count:" + countForLambdas);
    }

    /*
    This method creates 5 threads and passes
    worker class instances to threads (since it implements Runnable).
     */
    private static void runNonLambdaThreads() throws InterruptedException {

        System.out.println("Executing threads without lambdas");

        Worker worker1 = new Worker();
        Worker worker2 = new Worker();
        Worker worker3 = new Worker();
        Worker worker4 = new Worker();
        Worker worker5 = new Worker();

        Thread thread1 = new Thread(worker1);
        Thread thread2 = new Thread(worker2);
        Thread thread3 = new Thread(worker3);
        Thread thread4 = new Thread(worker4);
        Thread thread5 = new Thread(worker5);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        System.out.println("Worker count:" + Worker.count);
    }

    /*
    This method creates 5 threads and passes
    worker class instances to threads (since it implements Runnable).
    Non data race version.
     */
    private static void runNonLambdaThreadsWithoutDataRace() throws InterruptedException {

        System.out.println("Executing threads without lambdas (non data race version)");

        WorkerWithoutDataRace worker1 = new WorkerWithoutDataRace();
        WorkerWithoutDataRace worker2 = new WorkerWithoutDataRace();
        WorkerWithoutDataRace worker3 = new WorkerWithoutDataRace();
        WorkerWithoutDataRace worker4 = new WorkerWithoutDataRace();
        WorkerWithoutDataRace worker5 = new WorkerWithoutDataRace();

        Thread thread1 = new Thread(worker1);
        Thread thread2 = new Thread(worker2);
        Thread thread3 = new Thread(worker3);
        Thread thread4 = new Thread(worker4);
        Thread thread5 = new Thread(worker5);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        System.out.println("Worker count:" + WorkerWithoutDataRace.count);
    }
}
