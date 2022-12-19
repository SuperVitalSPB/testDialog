package com.example.testdialog.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.testdialog.R
import com.example.testdialog.data.MessagesInteractor
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

const val TAG = "MainViewModel"

class MessagesViewModel @Inject constructor(private val messagesInteractor: MessagesInteractor) : androidx.lifecycle.ViewModel() {

    var messages : MutableLiveData<ArrayList<String>>
    lateinit var observer : DisposableObserver<String>
    var period
        get() = messagesInteractor.period
        set(value) {
            observer.dispose()
            messagesInteractor.period = value
            observeData()
        }
    val error: MutableLiveData<Int> = MutableLiveData()

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

    fun checkPosition(position: String): Int? = try {
        val pos = position.toInt()
        if (pos >= (messages.value?.size ?: 0))
            throw Exception()
        pos
        } catch (e: Exception) {
            error.value = R.string.bad_position
            null
        }

    fun insertMessage(pos: String) {
        checkPosition(pos)?.let {position ->
            messages.value?.let {
                it.add(position, "source 2: ${position}")
                messages.postValue(it)
            }
        }
    }

    companion object {
        fun provideFactory(messagesInteractor: MessagesInteractor,
                           owner: SavedStateRegistryOwner,
                           defaultArgs: Bundle? = null, ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return MessagesViewModel(messagesInteractor) as T
                }
            }
    }
}