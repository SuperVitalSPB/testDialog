package com.example.testdialog.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.testdialog.data.MessagesInteractor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import java.util.concurrent.TimeUnit


const val TAG = "MainViewModel"

class MainViewModel constructor(private val messagesInteractor: MessagesInteractor) : androidx.lifecycle.ViewModel() {

    private lateinit var subscription : Observer<Long>

    var messages : MutableLiveData<ArrayList<String>> = MutableLiveData()


    @SuppressLint("CheckResult")
    fun observeData(period: Long) {
        val observable = Observable.interval(period, TimeUnit.MILLISECONDS)

        val observer = object : DisposableObserver<Long>() {
            override fun onNext(value: Long) {
                Log.d(TAG, "value: $value")
                if (value == 5L) {
                    dispose()
                    observeData(period%2)
                }
            }
            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }
            override fun onComplete() {
                TODO("Not yet implemented")
            }
        }

        observable.subscribe(observer)
    }

    private fun <T> getObserver(): Observer<T> {
        return Observer<T> {
            Log.d(TAG, "onChanged")
            messages.value?.let {
                it.add("Sourse 1: ${it.size}")
            }
        }
    }

}