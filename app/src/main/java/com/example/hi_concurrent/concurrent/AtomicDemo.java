package com.example.hi_concurrent.concurrent;

import java.time.temporal.ValueRange;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：haoshuai on 2021/3/2 17:50
 * 邮箱：
 * desc：一个用原子类修饰，一个用volatile修饰，在多线程的情况下做自增，然后输出最后的值
 */

public class AtomicDemo {
    public static void main(String[] args) {
        final AtomicTask atomicTask = new AtomicTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    atomicTask.incrementAtomic();
                    atomicTask.incrementVolatile();
                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("原子类的结果" + atomicTask.atomicInteger.get());
        System.out.println("volatile修饰的结果" + atomicTask.volatileCount);

    }

    static class AtomicTask{
        AtomicInteger atomicInteger = new AtomicInteger();
        volatile  int volatileCount = 0;
        void incrementAtomic(){
            atomicInteger.getAndIncrement();
        }

        void incrementVolatile(){
            volatileCount++;
        }
    }
}
