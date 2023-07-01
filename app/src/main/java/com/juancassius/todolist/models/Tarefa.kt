package com.juancassius.todolist.models

data class Tarefa (
    val tarefa: String? = null,
    val descricao: String? = null,
    val prioridade: Int? = null,
    val isChecked: Boolean? = false
    ){
}