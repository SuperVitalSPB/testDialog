package com.example.testdialog

import android.app.Application
import android.content.Context
import com.example.testdialog.dagger.AppComponent
import com.example.testdialog.dagger.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }