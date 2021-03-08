package com.example.hi_concurrent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import com.example.hi_concurrent.coroutine.CoroutineScene
import com.example.hi_concurrent.coroutine.CoroutineScene2_decompiled
import com.example.hi_concurrent.coroutine.CoroutineScene3
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ConcurrentTest.test(this)
        test.setOnClickListener {
            //CoroutineScene.startScene2()
           /* val callback = Continuation<String>(Dispatchers.Main){
                result ->
                Log.e(Companion.TAG, result.getOrNull())
            }
            CoroutineScene2_decompiled.request1(callback)*/
            lifecycleScope.launch {
                val content = CoroutineScene3.parseAssetsFile(assets, "loading_wave.json")
                Log.e(TAG, content)
            }
            Log.e(TAG, "after click")
          /*  lifecycleScope.async {  }
            lifecycleScope.launchWhenCreated {
                //是指当我们宿主的生命周期至少为 onCreate的时候才会启动
                whenCreated {
                    //这里的代码 当我们宿主的生命周期onCreate的时候才会执行，否则都是暂停
                }
                whenResumed {
                    //这里的代码 当我们宿主的生命周期onResume的时候才会执行，否则都是暂停

                }
                whenStarted {
                    //这里的代码 当我们宿主的生命周期onStart的时候才会执行，否则都是暂停

                }
            }

            lifecycleScope.launchWhenResumed {
                //是指当我们宿主的生命周期至少为 onResume的时候才会启动
            }*/
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}