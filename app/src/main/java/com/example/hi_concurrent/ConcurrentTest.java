package com.example.hi_concurrent;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 作者：haoshuai on 2021/3/2 16:05
 * 邮箱：
 * desc：
 */

public class ConcurrentTest {
    private static final String TAG = "ConcurrentTest";
    private static final int MSG_WHAT_1 = 1;
    private static volatile boolean hasNotify = false;

    public static void test(Context context) {
       /* class MyAsyncTask extends AsyncTask<String ,Integer,String >{
            @Override
            protected String doInBackground(String... params) {
                for (int i = 0; i < 10; i++) {
                    publishProgress(i*10);
                }
                return params[0];
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                Log.e(TAG, "onProgressUpdate: "+values[0] );
            }

            @Override
            protected void onPostExecute(String result) {
                Log.e(TAG, "onPostExecute: "+result );
            }
        }

        //适用于需要知道任务进行进度并更新UI的场景
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("execute myAsyncTask");

        //以这种方式提交的任务，所有任务串行执行，即先来后到，但是如果有其中有一条任务休眠了，后面的任务都将被阻塞
        //适用于串行任务执行
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: AsyncTask execute" );
            }
        });

        //适用于并发任务执行
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: AsyncTask.THREAD_POOL_EXECUTOR execute" );
            }
        });*/

      /*  for (int i = 0; i < 10; i++) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: "+System.currentTimeMillis() );
                }
            });
        }*/

        //适用于主线程需要和子线程通信的场景
        //适用于持续性任务，比如轮询
        /*HandlerThread handlerThread = new HandlerThread("handler-thread");
        handlerThread.start();

        MyHandler myHandler = new MyHandler(handlerThread.getLooper());
        myHandler.sendEmptyMessage(MSG_WHAT_1);*/

        //适用于多线程同步，一个线程需要等待另一个线程的执行结果，或部分结果
        //wait notify 调用顺序
      /*  final Object object = new Object();
        class Runnable1 implements Runnable{
            @Override
            public void run() {
                Log.e(TAG, "run: thread1 start");
                synchronized (object){
                    try {
                        if (!hasNotify){
                            object.wait(1000);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                Log.e(TAG, "run: thread1 end" );
            }
        }
        class Runnable2 implements Runnable{
            @Override
            public void run() {
                Log.e(TAG, "run: thread2 start");
                synchronized (object) {
                    object.notify();
                    hasNotify = true;
                }
                    Log.e(TAG, "run: thread2 end" );
                }
        }
        Thread thread1 = new Thread(new Runnable1());
        Thread thread2 = new Thread(new Runnable2());
        thread1.start();
        thread2.start();*/

        //一个线程需要等待另一个线程执行完才能继续的场景
        //join向当前线程插入一条任务
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run:1 "+System.currentTimeMillis() );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "run:2 "+System.currentTimeMillis() );
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "test: "+System.currentTimeMillis() );*/

    /*    final Object object = new Object();
        class Runnable1 implements Runnable{
            @Override
            public void run() {
                Log.e(TAG, "run: thread1 start");
                synchronized (object){
                    try {
                       Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                Log.e(TAG, "run: thread1 end" );
            }
        }
        class Runnable2 implements Runnable{
            @Override
            public void run() {
                synchronized (object){
                    Log.e(TAG, "run: thread2 start");
                    Log.e(TAG, "run: thread2 end" );
                }
            }
        }
        Thread thread1 = new Thread(new Runnable1());
        Thread thread2 = new Thread(new Runnable2());
        thread1.start();
        thread2.start();*/


        class LooperThread extends Thread {
            private Looper looper;

            public LooperThread(@NonNull String name) {
                super(name);

            }

            public Looper getLooper(){
                synchronized (this){
                    if (looper==null){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return looper;
            }
            @Override
            public void run() {
                Looper.prepare();
                synchronized (this){
                    looper = Looper.myLooper();
                    notify();
                }
                Looper.loop();
            }
        }
        LooperThread looperThread = new LooperThread("hao");
        looperThread.start();
        Handler handler = new Handler(looperThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, "handleMessage: "+msg.what );
                Log.e(TAG, "handleMessage: "+Thread.currentThread().getName() );
            }
        };
        handler.sendEmptyMessage(MSG_WHAT_1);
    }

    static class MyHandler extends Handler {
        public MyHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: " + msg.what);
            Log.e(TAG, "handleMessage: " + Thread.currentThread().getName());
        }
    }
}
