package demo.java.parallel_programming;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class CalendarUser extends Thread {
    private static final String[] WEEKDAYS = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    private static int today = 0;
    /*
    Using a reentrant read write lock, when a thread has acquired read lock,
    many reader threads can read, but writer threads will wait.
    When a thread acquired write lock, all threads have to wait for the
    writing thread to finish.
     */
    private static ReentrantReadWriteLock marker = new ReentrantReadWriteLock();
    private static Lock readMarker = marker.readLock();
    private static Lock writeMarker = marker.writeLock();

    CalendarUser(String name) {
        this.setName(name);
    }

    public void run() {
        while (today < WEEKDAYS.length - 1) {
            if (this.getName().contains("Writer")) {
                writeMarker.lock();
                try {
                    today = (today + 1) % 7;
                    System.out.println(this.getName() + " updated date to " + WEEKDAYS[today]);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    writeMarker.unlock();
                }
            } else {
                readMarker.lock();
                try {
                    System.out.println(this.getName() + " sees today is " + WEEKDAYS[today] + " total readers' count: " + marker.getReadLockCount());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    readMarker.unlock();
                }
            }
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new CalendarUser("Reader-" + i).start();
        }

        for (int i = 0; i < 2; i++) {
            new CalendarUser("Writer-" + i).start();
        }
    }
}
