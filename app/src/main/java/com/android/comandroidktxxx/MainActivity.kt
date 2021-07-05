package com.android.comandroidktxxx

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.rxLifeScope
import com.android.ktx.common.isNull
import com.android.ktx.coroutinescope.*
import com.android.ktx.udid.UdidUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("udid", UdidUtils.identity)

        rxLifeScope.launch {


            val text1 = Ktx.run { 121 }.await()//直接awiat就是同步
            val text2 = Ktx.run { 121 }.delay(100).onErrorReturn {
                3
            }.tryAwait()//直接awiat就是同步，会等上面执行完毕

            val text3 = Ktx.run { 121 }.async(this)//异步
            val text4 = Ktx.run { 121 }.async(this)//异步，和上面3同时

            //这里异步结果，必须执行await才会有结果
            text3.await()
            text4.await()

            val text5 = Ktx.run { 121 }.asFlow()//转flow


//            Ktx.run(Dispatchers.IO) {
//                Ktx.runMain { Log.e("俄罗斯套娃3", Thread.currentThread().name) }.await()
//
//                withContext(Dispatchers.Main) {
//                    Log.e("俄罗斯套娃4", Thread.currentThread().name)
//                }
//            }.async(this).await()


        }

//        rxLifeScope.launch({
//            val test1 = Ktx.run {
//                4 / 0
//            }.startDelay(1300)
//            val test2 = Ktx.run {
//                5 / 0
//            }.delay(1500)
//            Log.e("TAG", "onCreate:${test1.tryAwait()}   ${test2.tryAwait()} ")
//        }, {
//            Log.e("TAG", "onCreate error:${it.message}} ")
//        }, {
//            Log.e("TAG", "onCreate:{开始} ")
//        }, {
//            Log.e("TAG", "onCreate:{结束}} ")
//
//        })
//
//
//        val testList1 = arrayListOf<Int>()
//        val testList2 = arrayListOf<Int>(1, 2, 3)
//
//        val testString: String? = null
//
//        testList1.isNull({
//            Log.e("testList1", "不空")
//        }) {
//            Log.e("testList1", "空")
//        }
//        testList2.isNull({
//            Log.e("testList2", "不空")
//        }) {
//            Log.e("testList2", "空")
//        }
//        testString.isNull({
//            Log.e("testString", "不空")
//        }) {
//            Log.e("testString", "空")
//        }

    }

    val mMyHandle: TestHandler by lazy { TestHandler(Looper.getMainLooper()) }

    class TestHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                100 -> {
                    Log.e("测试", "handleMessage: ${msg.arg1}")
                }

            }

        }
    }

}