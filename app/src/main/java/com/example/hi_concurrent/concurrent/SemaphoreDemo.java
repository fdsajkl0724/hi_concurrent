package com.example.hi_concurrent.concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 作者：haoshuai on 2021/3/8 14:45
 * 邮箱：
 * desc：演示多人故宫游玩，但是同一时刻限流3人
 */

class SemaphoreDemo {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(3,true);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String name = Thread.currentThread().getName();

                        semaphore.acquire(2);

                        System.out.println(name + "获取到了许可证");
                        Thread.sleep(new Random().nextInt(5000));

                        semaphore.release(2);
                        System.out.println(name + "归还了许可证");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
