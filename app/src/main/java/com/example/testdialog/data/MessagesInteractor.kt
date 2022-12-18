package com.example.testdialog.data

class MessagesInteractor() {

    var period: Long = 100
        set(value) {
            repository.period = value
            field = value
        }
    val repository: Repository = Repository(period)

    fun getMessages() = repository.getMessages()
        .map { value -> "source 1: $value" }

}