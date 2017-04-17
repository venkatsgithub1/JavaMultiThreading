package com.java.multithreading;

/**
 * 
 * Class created to show how
 * one can implement Runnable
 * interface and start threads.
 * 
 * @author Venkat.
 *
 */
public class ImplementRunnableInterface implements Runnable {

	/**
	 * Main method is where the
	 * actual execution starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ImplementRunnableInterface i=new ImplementRunnableInterface();
		Thread t1=new Thread(i, "Thread 1");
		Thread t2=new Thread(i, "Thread 2");
		Thread t3=new Thread(i, "Thread 3");
		t1.start();
		t2.start();
		t3.start();
	}

	/**
	 * Logic in run () is executed 
	 * when the Thread is started.
	 */
	@Override
	public void run() {
		for (int i=1;i<=1000;i++) {
			System.out.println(i);
		}
	}

}
