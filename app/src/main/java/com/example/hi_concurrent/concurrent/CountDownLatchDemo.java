package com.example.hi_concurrent.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 作者：haoshuai on 2021/3/8 14:37
 * 邮箱：
 * desc：演示一个多人过山车的场景
 * 假设有五人乘坐过山车  等待5人全部准备好，才能发车
 */

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch downLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(4000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "准备好了");
                    downLatch.countDown();
                }
            }).start();
        }
        downLatch.await();
        System.out.println("所有人都准备好了");
    }
}
