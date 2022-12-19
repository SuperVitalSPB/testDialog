package com.example.testdialog.data

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class Repository() {
    var period: Long = 1000

    fun getMessages() = Observable.interval(period, TimeUnit.MILLISECONDS)

}