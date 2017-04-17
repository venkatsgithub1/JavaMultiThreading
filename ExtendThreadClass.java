package com.java.multithreading;

/**
 * The class is created to show
 * the usage of extending Thread
 * class and start threads using
 * the same.
 * 
 * @author Venkat
 *
 */
public class ExtendThreadClass extends Thread {

	/**
	 * Main method is the starting
	 * point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ExtendThreadClass e=new ExtendThreadClass();
		e.start();
		ExtendThreadClass ee=new ExtendThreadClass();
		ee.start();
	}

	/**
	 * The method run is overridden
	 * and contains the actual process
	 * that will be run when a start
	 * method is issued on the class
	 * that extends Thread class.
	 * 
	 */
	@Override
	public void run () {
		for (int i=0;i<1000;i++) {
			System.out.println(i);
		}
	}
}
