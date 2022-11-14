package com.android.comandroidktxxx

import android.app.Application
import com.android.ktx.provider.ContextProvider

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        ContextProvider.initCotext(this)
    }
}