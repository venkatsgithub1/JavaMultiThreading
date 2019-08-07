package demo.java.parallel_programming;

class CPUDrainer extends Thread {
    public void run() {
        while (true) {
        }
    }
}

public class ThreadProcess1 {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long usedKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024;

        System.out.println("Process ID:" + ProcessHandle.current().pid());
        System.out.println("Thread Count:" + Thread.activeCount());
        System.out.println("Memory usage:" + usedKB + " KB");

        // start 6 threads
        System.out.println("starting 2 cpu drainer threads:");
        for (int i = 0; i < 2; i++) {
            new CPUDrainer().start();
        }

        usedKB = (runtime.totalMemory() - runtime.freeMemory()) / 1024;

        System.out.println("Process ID:" + ProcessHandle.current().pid());
        System.out.println("Thread Count:" + Thread.activeCount());
        System.out.println("Memory usage:" + usedKB + " KB");

        /*
        After running the processes and monitor the process usage of java using pid, it must
        generally peak, since the threads are running forever.
         */
    }
}
