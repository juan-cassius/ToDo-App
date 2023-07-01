
package com.juancassius.todolist.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.juancassius.todolist.R
import com.juancassius.todolist.listItem.ItemTarefa
import com.juancassius.todolist.ui.theme.BLACK
import com.juancassius.todolist.ui.theme.Purple700
import com.juancassius.todolist.ui.theme.WHITE
import com.juancassius.todolist.viewmodels.TarefasViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDeTarefas(
    navController: NavController,
    tarefasViewModel: TarefasViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val userName = tarefasViewModel.recoverUserName().collectAsState(initial = "").value

    Scaffold(
        containerColor = BLACK,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Purple700
                ),
                title = {
                    Text(
                        color = WHITE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Lista de Tarefas"
                    )
                },
                actions = {
                    Text(
                        text = userName,
                        fontSize = 16.sp ,
                        fontWeight = FontWeight.Medium,
                        color = WHITE
                    )

                    TextButton(
                        onClick = {
                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.setTitle("FInalizar sessão")
                            alertDialog.setMessage("Deseja finalizar esta sessão?")
                            alertDialog.setPositiveButton("Sim"){_,_ ->
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate("login")
                            }
                            alertDialog.setNegativeButton("Não"){_, _ ->

                            }.show()
                        }
                    ) {
                        Text("Sair", fontSize = 16.sp, color = WHITE)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Purple700,
//              contentColor = WHITE,
                onClick = {
                            navController.navigate("salvaTarefa")
                          },
                shape = ShapeDefaults.ExtraLarge
            ){
                Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_floating), contentDescription = "'Add a work' button")
            }
        }
    ) {

        val listaDeTarefas = tarefasViewModel.getTarefas().collectAsState(mutableListOf()).value

        LazyColumn(
            modifier = Modifier.padding( top= 65.dp )
        ){
            itemsIndexed(listaDeTarefas){
                position, _ ->
               ItemTarefa(position, listaDeTarefas, context, navController, tarefasViewModel)
            }
        }
    }
}