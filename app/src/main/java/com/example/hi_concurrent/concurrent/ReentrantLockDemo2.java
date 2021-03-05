package com.example.hi_concurrent.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 作者：haoshuai on 2021/3/3 10:56
 * 邮箱：
 * desc：演示多个线程去打印纸张 每个线程打印两张（ ReentrantLock 公平锁 非公平锁）
 * 公平锁：交易
 * 非公平锁：synchronized
 */

public class ReentrantLockDemo2 {

    static class ReentrantLockTask{
        ReentrantLock lock = new ReentrantLock();

        void print(){
            String name = Thread.currentThread().getName();
            try {
                lock.lock();
                //打印两次
                System.out.println(name + "第一次打印");
                Thread.sleep(1000);
                lock.unlock();

                lock.lock();
                System.out.println(name + "第二次打印");
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTask task = new ReentrantLockTask();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.print();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}
