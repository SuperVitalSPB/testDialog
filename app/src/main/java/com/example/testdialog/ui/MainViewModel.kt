package com.example.testdialog.ui

import androidx.lifecycle.MutableLiveData

class MainViewModel  : androidx.lifecycle.ViewModel {

    private val messagesInternal: MutableLiveData<List<String>> =
        MutableLiveData<List<String>>().apply { value = emptyList() }

    val messagesList: MutableLiveData<List<Strings>> = messagesInternal




}