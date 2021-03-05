package com.example.hi_concurrent.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：haoshuai on 2021/3/2 17:22
 * 邮箱：
 * desc：卖票的并发场景--不设置并发安全措施，会出现多人买到同一张票
 * 如果synchronized加在方法上，未获取到锁的线程，只能排队，不能访问
 * 如果synchronized加在代码块上，未获取到锁的线程，可以访问同步代码块之外的代码
 * 加在static方法上，就相当于是给Class对象加锁，由于在jvm中只会存在一份class对象，所以无论此时是不是同一个java对象，只能排队访问
 */

public class SynchronizedDemo {
    static List<String> tickets = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            tickets.add("票_"+(i+1));
        }
        sellTickets();
    }

     static void sellTickets() {
        final SynchronizedTestDemo testDemo = new SynchronizedTestDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //testDemo.printThreadName();
//                    new SynchronizedTestDemo().printThreadName();
                    testDemo.printThreadName();
                }
            }).start();
        }

    }
     static class SynchronizedTestDemo{
        public SynchronizedTestDemo() {
        }

        public  void printThreadName() {
            String name = Thread.currentThread().getName();
            System.out.println("买票人：" + name + "准备好了...");
            synchronized (this){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("买票人：" + name + "正在买...");
            }

            System.out.println("买票人" + name + "买到的票是..." + tickets.remove(0));
        }
    }
}
