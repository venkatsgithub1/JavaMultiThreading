package com.java.multithreading;

public class UsingThreadJoin implements Runnable {
	public static void main(String[] args) {
		Thread t1=new Thread(new UsingThreadJoin(), "Thread 1");
		Thread t2=new Thread(new UsingThreadJoin(), "Thread 2");
		Thread t3=new Thread(new UsingThreadJoin(), "Thread 3");
		t1.start();
		try {
			t1.join(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t3.start();
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" started");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" slept for 4000ms and ended");
	}
}
