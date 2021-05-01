package com.java.multithreading.jakob_jenkov.javaMemoryModel;

public class SeparateObjects {
    public static void main(String[] args) {
        /*
        Now, heap has one runnable1 object referenced by thread1,
        another referenced by thread2
         */

        Runnable runnable1 = new MyRunnable();
        Runnable runnable2 = new MyRunnable();

        /*
        Each thread creates a copy of local variable i
        in its own thread stack.
        Local variables are never shared between threads.
        Each runnable object in heap has its own count field.
        That means each thread has its own count field.
         */
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();
    }
}
