package com.juancassius.todolist.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.juancassius.todolist.components.CustomButton
import com.juancassius.todolist.components.CustomTextField
import com.juancassius.todolist.constants.Constants
import com.juancassius.todolist.ui.theme.GREEN_DISABLED_RADIO
import com.juancassius.todolist.ui.theme.GREEN_SELECTED_RADIO
import com.juancassius.todolist.ui.theme.Purple700
import com.juancassius.todolist.ui.theme.RED_RADIO
import com.juancassius.todolist.ui.theme.WHITE
import com.juancassius.todolist.ui.theme.YELLOW_DISABLED_RADIO
import com.juancassius.todolist.ui.theme.YELLOW_SELECTED_RADIO
import com.juancassius.todolist.viewmodels.TarefasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalvaTarefa(
    navController: NavController,
    tarefasViewModel: TarefasViewModel = hiltViewModel()
){

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        containerColor = WHITE,
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
                        text = "Adicionar Tarefa"
                    )
                }
            )
        }
    ){

        var titleField by remember {
            mutableStateOf("")
        }

        var descriptionField by remember {
            mutableStateOf("")
        }

        var lowPriority by remember {
            mutableStateOf(false)
        }

        var normalPriority by remember {
            mutableStateOf(false)
        }

        var highPriority by remember {
            mutableStateOf(false)
        }

        var priority by remember {
            mutableStateOf("no")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 65.dp)
        ) {
            CustomTextField(
                value = titleField,
                onChange = {
                           titleField = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, 20.dp, 20.dp, 0.dp),
                labelText = "Título da tarefa",
                keyboardType = KeyboardType.Text
            )
            CustomTextField(
                value = descriptionField,
                onChange = {
                    descriptionField = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, 20.dp, 20.dp, 0.dp)
                    .height(150.dp),
                labelText = "Descrição",
                keyboardType = KeyboardType.Text,
                maxLines = 5
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = "Nível de prioridade:")
                RadioButton(
                    selected = lowPriority,
                    onClick = {
                        lowPriority = !lowPriority
                        priority = "low"
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = GREEN_DISABLED_RADIO,
                        selectedColor =  GREEN_SELECTED_RADIO
                    )
                )
                RadioButton(
                    selected = normalPriority,
                    onClick = {
                        normalPriority = !normalPriority
                        priority = "normal"
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = YELLOW_DISABLED_RADIO,
                        selectedColor =  YELLOW_SELECTED_RADIO
                    )
                )
                RadioButton(
                    selected = highPriority,
                    onClick = {
                        highPriority = !highPriority
                        priority = "high"
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RED_RADIO,
                        selectedColor =  RED_RADIO
                    )
                )
            }
            Row {
                CustomButton(
                    onClick = {

                        var mensagem: Boolean

                        scope.launch(Dispatchers.IO){
//                            mensagem = false

                            if(titleField.isEmpty()){
                                mensagem = false
                            } else {
                                when(priority){
                                    "low" -> {
                                        tarefasViewModel.salvaTarefa(titleField, descriptionField, Constants.LOW_PRIORITY)
                                        mensagem = true
                                    }
                                    "normal" -> {
                                        tarefasViewModel.salvaTarefa(titleField, descriptionField, Constants.NORMAL_PRIORITY)
                                        mensagem = true
                                    }
                                    "high" -> {
                                        tarefasViewModel.salvaTarefa(titleField, descriptionField, Constants.HIGH_PRIORITY)
                                        mensagem = true
                                    }
                                    else -> {
                                        tarefasViewModel.salvaTarefa(titleField, descriptionField, Constants.NO_PRIORITY)
                                        mensagem = true
                                    }
                                }
                            }

                            scope.launch(Dispatchers.Main){
                                if(mensagem){
                                    Toast.makeText(context,"Sucesso ao salvar a tarefa!",Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context,"O título da tarefa precisa ser preenchido!",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    text = "Salvar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(40.dp)
                )
            }
        }
    }
}