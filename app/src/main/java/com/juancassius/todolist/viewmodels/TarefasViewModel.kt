package com.juancassius.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juancassius.todolist.models.Tarefa
import com.juancassius.todolist.repository.TarefasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarefasViewModel @Inject constructor(private val tarefasRepository: TarefasRepository): ViewModel() {

    private val _todasAsTarefas = MutableStateFlow<MutableList<Tarefa>>(mutableListOf())
    private val todasAsTarefas: StateFlow<MutableList<Tarefa>> = _todasAsTarefas

    private val _userName = MutableStateFlow<String>("")
    private val userName: StateFlow<String> = _userName

    fun salvaTarefa(tarefa: String, descricao: String, prioridade: Int, isChecked: Boolean = false){
        viewModelScope.launch{
            tarefasRepository.salvaTarefa(tarefa, descricao, prioridade, isChecked)
        }
    }

    fun getTarefas(): Flow<MutableList<Tarefa>>{

        viewModelScope.launch {
            tarefasRepository.getTarefas().collect{
                _todasAsTarefas.value = it
            }
        }

        return todasAsTarefas
    }

    fun deletaTarefa(tarefa: String){
        viewModelScope.launch {
            tarefasRepository.deletaTarefa(tarefa)
        }
    }

    fun atualizaCheckBoxTarefa(tarefa: String, newIsCheckedState: Boolean){
        viewModelScope.launch {
            tarefasRepository.atualizaCheckboxTarefa(tarefa, newIsCheckedState)
        }
    }

    fun recoverUserName(): Flow<String>{
        viewModelScope.launch {
            tarefasRepository.recoverUserName().collect{
                _userName.value = it
            }
        }
        return userName
    }

}