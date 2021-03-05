package com.example.hi_concurrent.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 作者：haoshuai on 2021/3/3 10:56
 * 邮箱：
 * desc：演示生产者与消费者的场景  利用的是ReentrantLock condition条件对象，能够指定唤醒某个线程去工作
 * 生产者：一个boss去生产砖 砖的序号为偶数，让工人2去搬，奇数号让工人去搬
 * 消费者 是两个工人 有砖就搬  没砖就休息
 */

public class ReentrantLockDemo3 {

    static class ReentrantLockTask{
        private Condition worker1Condition,worker2Condition;
        ReentrantLock lock = new ReentrantLock(true);

        volatile int flag = 0;//砖的序列号

        public ReentrantLockTask() {
            worker1Condition =lock.newCondition();
            worker2Condition = lock.newCondition();
        }

        void work1(){
            try{
                lock.lock();
                if (flag==0||flag%2==0){
                    System.out.println("work1无转可搬 休息");
                    worker1Condition.await();
                }
                System.out.println("work1 搬到的砖是 " + flag);
                flag=0;
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        void work2(){
            try{
                lock.lock();
                if (flag==0||flag%2!=0){
                    System.out.println("work2无转可搬 休息");
                    worker2Condition.await();
                }
                System.out.println("work2 搬到的砖是 " + flag);
                flag=0;
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        void boss(){
            try {
                lock.lock();
                flag = new Random().nextInt(100);
                if (flag%2==0){
                    worker2Condition.signal();
                    System.out.println("生产出来砖 唤醒工人2搬"+flag);
                }else{
                    worker1Condition.signal();
                    System.out.println("生产出来砖 唤醒工人1搬"+flag);
                }
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTask task = new ReentrantLockTask();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    task.work1();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    task.work2();
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            task.boss();
        }
    }

}
