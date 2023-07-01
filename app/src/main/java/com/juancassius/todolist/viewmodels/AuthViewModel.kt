package com.juancassius.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juancassius.todolist.listener.AuthListener
import com.juancassius.todolist.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel(){

    fun cadastraUsuario(nome: String, email: String, senha: String, authListener: AuthListener){
        viewModelScope.launch {
            authRepository.cadastraUsuario(nome, email, senha, authListener)
        }
    }

    fun login(email: String, senha: String, authListener: AuthListener){
        viewModelScope.launch {
            authRepository.login(email, senha, authListener)
        }
    }

    fun verifyLogin(): Flow<Boolean>{
        return authRepository.verifyLogin()
    }
}