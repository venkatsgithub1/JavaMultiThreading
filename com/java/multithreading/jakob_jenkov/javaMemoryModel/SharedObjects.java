package com.java.multithreading.jakob_jenkov.javaMemoryModel;

public class SharedObjects {
    public static void main(String[] args) {
        /*
        Now, heap has one runnable1 object referenced by thread1,
        same referenced by thread2
         */

        Runnable runnable1 = new MyRunnable();

        /*
        Each thread creates a copy of local variable i
        in its own thread stack.
        Local variables are never shared between threads.
        Each runnable object in heap has its own count field.
        That means both threads has access to the same count field.
        count here is a shared variable.
         */
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable1);

        thread1.start();
        thread2.start();
    }
}
