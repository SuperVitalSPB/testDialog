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

    var messages : MutableLiveData<ArrayList<String>>
    lateinit var observer : DisposableObserver<String>
    var period
        get() = messagesInteractor.period
        set(value) {
            observer.dispose()
            messagesInteractor.period = value
            observeData()
        }

    init {
        messages =  MutableLiveData()
        messages.value = ArrayList()
    }

    fun observeData() {
        val observable = messagesInteractor.getMessages()
        observer = object : DisposableObserver<String>() {
            override fun onNext(value: String) {
                messages.value?.let {
                    it.add("$value ${it.size}" )
                    messages.postValue(it)
                }
            }
            override fun onError(err: Throwable) {
                Log.d(TAG, "onError: ${err.message}")
            }
            override fun onComplete() {
                Log.d(TAG, "onComplete")
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