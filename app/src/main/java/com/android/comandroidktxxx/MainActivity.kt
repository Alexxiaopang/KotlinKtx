package com.android.comandroidktxxx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.rxLifeScope
import com.android.ktx.common.isNull
import com.android.ktx.coroutinescope.*
import com.android.ktx.spanner.Spanner
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rxLifeScope.launch({
            val test1 = Ktx.run {
                4 / 0
            }.startDelay(1300)
            val test2 = Ktx.run {
                5 / 0
            }.delay(1500)
            Log.e("TAG", "onCreate:${test1.tryAwait()}   ${test2.tryAwait()} ")
        }, {
            Log.e("TAG", "onCreate error:${it.message}} ")
        }, {
            Log.e("TAG", "onCreate:{开始} ")
        }, {
            Log.e("TAG", "onCreate:{结束}} ")

        })


        val testList1 = arrayListOf<Int>()
        val testList2 = arrayListOf<Int>(1, 2, 3)

        val testString:String?=null

        testList1.isNull({
            Log.e("testList1", "不空")
        }){
            Log.e("testList1", "空")
        }
        testList2.isNull({
            Log.e("testList2", "不空")
        }){
            Log.e("testList2", "空")
        }
        testString.isNull({
            Log.e("testString", "不空")
        }){
            Log.e("testString", "空")
        }

    }
}