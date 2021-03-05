package com.example.hi_concurrent.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 作者：haoshuai on 2021/3/3 15:00
 * 邮箱：
 * desc： 利用ReentrantReadWriteLock实现 多人在线文档查看与编辑的功能
 */

public class ReentrantReadWriteLockDemo {

    static class  ReentrantReadWriteLockTask{
        private final ReentrantReadWriteLock.ReadLock readLock;
        private final ReentrantReadWriteLock.WriteLock writeLock;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public ReentrantReadWriteLockTask() {
            readLock = lock.readLock();
            writeLock = lock.writeLock();
        }

        void read(){
            String name = Thread.currentThread().getName();
            try {
                readLock.lock();
                System.out.println("线程" + name + "正在读取数据...");
                Thread.sleep(1000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                readLock.unlock();
                System.out.println("线程" + name + "释放了读锁...");
            }
        }

        void write(){
            String name = Thread.currentThread().getName();
            try {
                writeLock.lock();
                System.out.println("线程" + name + "正在写入数据...");
                Thread.sleep(1000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                writeLock.unlock();
                System.out.println("线程" + name + "释放了写锁...");
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantReadWriteLockTask task = new ReentrantReadWriteLockTask();

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task.read();
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task.write();
                }
            }).start();
        }
    }

}
