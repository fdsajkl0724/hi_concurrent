package com.example.hi_concurrent.coroutine

import android.util.Log
import kotlinx.coroutines.*

/**
 * 作者：haoshuai on 2021/3/8 10:07
 * 邮箱：
 * desc：
 */
object CoroutineScene{
    private const val TAG = "CoroutineScene"
    /**
     * 依次启动三个子线程，并以同步的方式拿到他们的返回值，进而更新UI
     */
    fun startScene1(){
        GlobalScope.launch (Dispatchers.Main){
            Log.e(TAG, "coroutine is running")
            val result1 = request1()
            val result2 = request2(result1)
            val result3 = request3(result2)

            updateUI(result3)
        }
        Log.e(TAG, "coroutine has launched " )
    }

    /**
     * 启动一个协程，先执行request1，完了之后，同时运行request2 和 request3.这两并发都结束了才执行更新UI
     */
    fun startScene2(){
        GlobalScope.launch (Dispatchers.Main){
            Log.e(TAG, "coroutine is running")
             val result1 = request1()
            val deferred2 = GlobalScope.async { request2(result1) }
            val deferred3 = GlobalScope.async { request3(result1) }
            updateUI(deferred2.await(),deferred3.await())
        }
    }

    private fun updateUI(result2: String, result3: String) {
        Log.e(TAG, "updateUI: work on ${Thread.currentThread().name}" )
        Log.e(TAG, "updateUI: parameter..." +result2+"---"+result3 )
    }

    private fun updateUI(result3: String) {
        Log.e(TAG, "updateUI: work on ${Thread.currentThread().name}" )
        Log.e(TAG, "updateUI: parameter..." +result3 )
    }

    //suspend关键字的作用->
    suspend fun request1():String{
        delay(2*1000)//不会暂停线程，但会暂停当前所在的协程
        Log.e(TAG, "request1:work on ${Thread.currentThread().name}" )
        return "result form request1"
    }

    suspend fun request2(result1: String):String{
        delay(2*1000)
        Log.e(TAG, "request2:work on ${Thread.currentThread().name}" )
        return "result form request2"
    }

    suspend fun request3(result2: String):String{
        delay(2*1000)
        Log.e(TAG, "request3:work on ${Thread.currentThread().name}" )
        return "result form request3"
    }
}