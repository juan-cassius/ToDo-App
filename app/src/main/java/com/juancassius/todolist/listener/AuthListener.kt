package com.juancassius.todolist.listener

interface AuthListener {
    fun onSuccess(message: String, viewPage: String)
    fun onFailure(error: String)
}