package demo.java.parallel_programming;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerMethodDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

        System.out.println(atomicInteger.addAndGet(10)); // will get 10, returns val after
        System.out.println(atomicInteger.getAndAdd(10)); // will get 10, returns prev
        System.out.println(atomicInteger.incrementAndGet()); // will get 21
        System.out.println(atomicInteger.getAndIncrement()); // will get 21, because previous
    }
}
