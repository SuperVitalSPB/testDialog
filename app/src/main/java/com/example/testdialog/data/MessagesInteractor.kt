package com.example.testdialog.data

import javax.inject.Inject

class MessagesInteractor @Inject constructor (val repository: Repository) {

    var period: Long = 1000
        set(value) {
            repository.period = value
            field = value
        }

    fun getMessages() = repository.getMessages()
        .map { value -> "source 1: " }

}