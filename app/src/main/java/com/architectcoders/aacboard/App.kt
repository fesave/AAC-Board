package com.architectcoders.aacboard

import android.app.Application
import com.architectcoders.aacboard.di.initDi

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }
}