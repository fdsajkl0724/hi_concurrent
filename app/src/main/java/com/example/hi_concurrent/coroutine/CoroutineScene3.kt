package com.example.hi_concurrent.coroutine

import android.content.res.AssetManager
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * 作者：haoshuai on 2021/3/8 14:05
 * 邮箱：
 * desc：演示以异步的方式读取assets目录下的文件，并且适配协程的写法，成为真正的挂起函数
 * 方便调用方 直接以同步的方式拿到返回值
 */

object CoroutineScene3{
    private const val TAG = "CoroutineScene3"
    suspend fun parseAssetsFile(assetManager:AssetManager,fileName:String):String{
        return suspendCancellableCoroutine { continuation->
            Thread(Runnable {
                val inputStream = assetManager.open(fileName)
                val br = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                var stringBuilder = StringBuilder()
                do {
                    line = br.readLine()
                    if (line != null) stringBuilder.append(line) else break
                } while (true)
                inputStream.close()
                br.close()
                Thread.sleep(2000)
                Log.e(TAG, "parseAssetsFile completed" )
                continuation.resumeWith(Result.success(stringBuilder.toString()))
            }).start()

        }
    }
}