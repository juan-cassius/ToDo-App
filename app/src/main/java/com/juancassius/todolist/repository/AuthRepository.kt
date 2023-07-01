package com.juancassius.todolist.repository

import com.juancassius.todolist.datasource.Auth
import com.juancassius.todolist.listener.AuthListener
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(private val auth: Auth) {

    fun cadastraUsuario(nome: String, email: String, senha: String, authListener: AuthListener){
        auth.cadastraUsuario(nome, email, senha, authListener)
    }

    fun login(email:String, senha: String, authListener: AuthListener){
        auth.login(email, senha, authListener)
    }

    fun verifyLogin(): Flow<Boolean> {
        return auth.verifyLogin()
    }
}