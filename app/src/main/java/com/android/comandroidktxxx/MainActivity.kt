package com.android.comandroidktxxx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.rxLifeScope
import com.android.ktx.coroutinescope.*
import com.android.ktx.spanner.Spanner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rxLifeScope.launch({
            val test1 = Ktx.run {
               4/0
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


    }
}