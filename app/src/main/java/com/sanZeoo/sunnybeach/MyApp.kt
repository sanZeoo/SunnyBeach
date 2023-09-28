package com.sanZeoo.sunnybeach

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp :Application() {

    companion object{
         @SuppressLint("StaticFieldLeak")
         lateinit var CONTEXT :Context

    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this

        Timber.plant(Timber.DebugTree());

    }
}