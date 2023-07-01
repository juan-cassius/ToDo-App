package com.juancassius.todolist.datasource

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.juancassius.todolist.listener.AuthListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Auth @Inject constructor() {

    var auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val _verifyLoginStatus = MutableStateFlow<Boolean>(false)
    val verifyLoginStatus: StateFlow<Boolean> = _verifyLoginStatus

    fun cadastraUsuario(nome: String, email:String, senha: String, authListener: AuthListener){

        if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
            authListener.onFailure("Preencha todos os campos!")
        } else {
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { cadastraUsuario ->
                    if(cadastraUsuario.isSuccessful) {
//                        authListener.onSuccess("Sucesso ao cadastrar o usuário!")

                        val userID = auth.currentUser?.uid.toString()

                        val userDataHashMap = hashMapOf(
                            "nome" to nome,
                            "email" to email,
                            "userID" to userID
                        )

                        db
                            .collection("users")
                            .document(userID).set(userDataHashMap)
                            .addOnCompleteListener {
                                authListener.onSuccess(
                                    "Sucesso ao cadastrar o usuário!",
                                    "login"
                                )
                            }
                            .addOnFailureListener {
                                authListener.onFailure("Erro inesperado!")
                            }
                    }
                }
                .addOnFailureListener { exception ->

                    val error = when(exception){
                        is FirebaseAuthUserCollisionException -> "Email já está cadastrado, favor utilizar outro email."
                        is FirebaseAuthWeakPasswordException -> "A senha deve ter pelo menos 6 caracteres"
                        is FirebaseNetworkException -> "Verifique sua conexão com a internet"
                        else -> "Email inválido!"
                    }

                    authListener.onFailure(error)
                }
        }
    }

    fun login(email: String, senha:  String, authListener: AuthListener){

        if(email.isEmpty() || senha.isEmpty()){
            authListener.onFailure("Preencha todos os campos!")
        }else{
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { auth ->
                if (auth.isSuccessful){
                    authListener.onSuccess("Login realizado com sucesso", "listaDeTarefas")
                }
            }
                .addOnFailureListener { exception ->

                    val error = when(exception){
                        is FirebaseAuthInvalidCredentialsException -> "Senha inválida"
                        is FirebaseNetworkException -> "Verifique sua conexão com a internet"
                        else -> "Email inválido"
                    }

                    authListener.onFailure(error)
                }
        }
    }

    fun verifyLogin(): Flow<Boolean>{
        val currentUser = FirebaseAuth.getInstance().currentUser

        _verifyLoginStatus.value = currentUser != null
        return verifyLoginStatus
    }
}