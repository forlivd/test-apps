package com.study.searchbook.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {
    companion object{

    }

    override fun onCreate() {
        super.onCreate()
    }
}