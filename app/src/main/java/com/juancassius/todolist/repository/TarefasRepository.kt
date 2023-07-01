package com.juancassius.todolist.repository

import com.juancassius.todolist.datasource.DataSource
import com.juancassius.todolist.models.Tarefa
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TarefasRepository @Inject constructor(private val dataSource: DataSource){

    fun salvaTarefa(tarefa: String, descricao: String, prioridade: Int, isChecked: Boolean = false){
        dataSource.salvaTarefa(tarefa, descricao, prioridade, isChecked)
    }

    fun getTarefas(): Flow<MutableList<Tarefa>>{
        return dataSource.getTarefas()
    }

    fun deletaTarefa(tarefa: String){
        dataSource.deletaTarefa(tarefa)
    }

    fun atualizaCheckboxTarefa(tarefa: String, newIsCheckedState: Boolean){
        dataSource.atualizaCheckBoxTarefa(tarefa, newIsCheckedState)
    }

    fun recoverUserName(): Flow<String>{
        return dataSource.recoverUserName()
    }

}