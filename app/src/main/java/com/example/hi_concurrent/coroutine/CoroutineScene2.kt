package com.example.hi_concurrent.coroutine

import android.util.Log

/**
 * 作者：haoshuai on 2021/3/8 11:15
 * 邮箱：
 * desc：
 */
object CoroutineScene2 {
    private const val TAG = "CoroutineScene2"

    suspend fun request2():String{
        Log.e(TAG, "request2:completed" )
        return "result from request2"
    }

    suspend fun request1():String{
        val request2 = request2();
        return "result from request1  "+request2;
    }
}