package com.example.testdialog.data

class InteractorMessages(interval: Int) {

    fun getMessages() = Repository().getMessages()

}