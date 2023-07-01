package com.juancassius.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juancassius.todolist.components.Cadastro
import com.juancassius.todolist.ui.theme.ToDoListTheme
import com.juancassius.todolist.viewmodels.AuthViewModel
import com.juancassius.todolist.viewmodels.TarefasViewModel
import com.juancassius.todolist.views.ListaDeTarefas
import com.juancassius.todolist.views.Login
import com.juancassius.todolist.views.SalvaTarefa
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {

                val navController = rememberNavController()
                val tarefasViewModel: TarefasViewModel = hiltViewModel()
                val authViewModel: AuthViewModel = hiltViewModel()

                NavHost(navController, startDestination = "login") {
                    composable(
                        route = "listaDeTarefas"
                    ){
                        ListaDeTarefas(navController = navController, tarefasViewModel)
                    }
                    composable(
                        route = "salvaTarefa"
                    ){
                        SalvaTarefa(navController = navController, tarefasViewModel)
                    }
                    composable(
                        route = "login"
                    ){
                        Login(navController = navController, authViewModel)
                    }
                    composable(
                        route = "cadastro"
                    ){
                        Cadastro(navController = navController, authViewModel)
                    }
                }

            }
        }
    }
}
