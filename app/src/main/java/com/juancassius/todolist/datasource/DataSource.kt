package com.juancassius.todolist.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.juancassius.todolist.models.Tarefa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class DataSource @Inject constructor(){

   private val db = FirebaseFirestore.getInstance()

    private val _todasAsTarefas = MutableStateFlow<MutableList<Tarefa>>(mutableListOf())
    private val todasAsTarefas: StateFlow<MutableList<Tarefa>> = _todasAsTarefas

    private val _userName = MutableStateFlow<String>("")
    private val userName: StateFlow<String> = _userName

    fun salvaTarefa(tarefa: String, descricao: String, prioridade: Int, isChecked: Boolean){

        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val mapTarefa = hashMapOf(
            "tarefa" to tarefa,
            "descricao" to descricao,
            "prioridade" to prioridade,
            "isChecked" to isChecked
        )

        db.collection("tarefas").document(userID)
            .collection("tarefas_user").document(tarefa)
            .set(mapTarefa)
            .addOnCompleteListener{}
            .addOnFailureListener{}

    }
    fun getTarefas(): Flow<MutableList<Tarefa>>{

        val listaDeTarefas: MutableList<Tarefa> = mutableListOf()
        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()


        db.collection("tarefas").document(userID)
            .collection("tarefas_user")
            .get()
            .addOnCompleteListener{ querySnapshopt ->
                if (querySnapshopt.isSuccessful){
                    for(document in querySnapshopt.result){
                        val tarefa = document.toObject( Tarefa::class.java )
                        listaDeTarefas.add(tarefa)
                        _todasAsTarefas.value = listaDeTarefas
                    }
                }

            }
            .addOnFailureListener{}

        return todasAsTarefas
    }

    fun deletaTarefa(tarefa: String){

        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()


        db.collection("tarefas").document(userID)
            .collection("tarefas_user").document(tarefa)
            .delete()
            .addOnCompleteListener{

            }.addOnFailureListener{

            }
    }

    fun atualizaCheckBoxTarefa(tarefa: String, newIsCheckedState: Boolean){

        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

        db.collection("tarefas").document(userID)
            .collection("tarefas_user").document(tarefa)
            .update("isChecked", newIsCheckedState)
            .addOnCompleteListener{}
            .addOnFailureListener{}
    }

    fun recoverUserName(): Flow<String>{

        val userID =  FirebaseAuth.getInstance().currentUser?.uid.toString()
        db.collection("users").document(userID).get().addOnCompleteListener {
                documentSnapshot ->
                    if (documentSnapshot.isSuccessful) {
                        val nome = documentSnapshot.result.getString("nome").toString()
                        _userName.value = nome
                    }
        }

        return userName
    }

}