package com.example.hi_concurrent.concurrent;

import java.time.temporal.ValueRange;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 作者：haoshuai on 2021/3/3 10:56
 * 邮箱：
 * desc：演示多个线程去竞争锁的 用法
 */

public class ReentrantLockDemo {

    static class ReentrantLockTask{
        ReentrantLock reentrantLock = new ReentrantLock();
        void buyTicket(){
            String name = Thread.currentThread().getName();
            try{
                reentrantLock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(100);
                System.out.println(name + ":买好了");

                reentrantLock.lock();
                System.out.println(name + ":又准备好了");
                Thread.sleep(100);
                System.out.println(name + ":又买好了");

                reentrantLock.lock();
                System.out.println(name + ":又准备好了");
                Thread.sleep(100);
                System.out.println(name + ":又买好了");


            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {

                reentrantLock.unlock();
                reentrantLock.unlock();
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTask task = new ReentrantLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.buyTicket();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

}
