package com.example.testdialog.ui

import androidx.lifecycle.MutableLiveData
import com.example.testdialog.data.InteractorMessages

class MainViewModel constructor(val interactorMessages: InteractorMessages) : androidx.lifecycle.ViewModel() {

    private val messagesInternal: MutableLiveData<List<String>> =
        MutableLiveData<List<String>>().apply { value = emptyList() }

    val messagesList: MutableLiveData<List<String>> = messagesInternal





}