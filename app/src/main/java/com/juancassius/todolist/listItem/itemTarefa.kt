package com.juancassius.todolist.listItem

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.juancassius.todolist.R
import com.juancassius.todolist.models.Tarefa
import com.juancassius.todolist.ui.theme.GREEN_SELECTED_RADIO
import com.juancassius.todolist.ui.theme.RED_RADIO
import com.juancassius.todolist.ui.theme.WHITE
import com.juancassius.todolist.ui.theme.YELLOW_SELECTED_RADIO
import com.juancassius.todolist.viewmodels.TarefasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ItemTarefa(
    position: Int,
    listaDeTarefas: MutableList<Tarefa>,
    context: Context,
    navController: NavController,
    tarefasViewModel: TarefasViewModel = hiltViewModel()
) {

    val tituloDaTarefa = listaDeTarefas[position].tarefa
    val descricaoDaTarefa = listaDeTarefas[position].descricao
    val prioridadeDaTarefa = listaDeTarefas[position].prioridade
    val isCheckedDaTarefa = listaDeTarefas[position].isChecked

    val nivelDePrioridade: String = when(prioridadeDaTarefa) {
        0 -> { "Sem prioridade" }
        1 -> { "Prioridade: Baixa" }
        2 -> { "Prioridade: Normal" }
        3 -> { "Prioridade: Alta" }
        else -> { "Prioridade: Alta" }
    }

    val corDaPrioridade: Color = when(prioridadeDaTarefa){
        0 -> { Color.Black }
        1 -> { GREEN_SELECTED_RADIO }
        2 -> { YELLOW_SELECTED_RADIO }
        3 -> { RED_RADIO }
        else -> { RED_RADIO }
    }

    val scope = rememberCoroutineScope()

    var isChecked by remember {
        mutableStateOf(isCheckedDaTarefa)
    }

    fun deleteDialog(){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog
            .setTitle("Deletar Tarefa")
            .setMessage("Deseja excluir a tarefa?")
            .setPositiveButton("Sim"){ _, _ ->
                tarefasViewModel.deletaTarefa(tituloDaTarefa.toString())
                scope.launch(Dispatchers.Main){
                    listaDeTarefas.removeAt(position)
                    navController.navigate("listaDeTarefas")
                    Toast
                        .makeText(context,"Tarefa excluída com sucesso", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Não"){ _, _ ->
            }
            .show()
    }

    Card(
        colors = CardDefaults.cardColors( containerColor = WHITE ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        ConstraintLayout(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            val (
                stringTitle, stringDescricao, cardPrioridade,
                stringPrioridade, buttonDeleta, checkoxTarefaConcluida
            ) = createRefs()

            Text(
                text = tituloDaTarefa.toString(),
                modifier = Modifier.constrainAs(stringTitle){
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )
            Text(
                text = descricaoDaTarefa.toString(),
                modifier = Modifier.constrainAs(stringDescricao){
                    top.linkTo(stringTitle.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )
            Text(
                text = nivelDePrioridade,
                modifier = Modifier.constrainAs(stringPrioridade){
                    top.linkTo(stringDescricao.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )
            Card(
                colors = CardDefaults.cardColors( containerColor = corDaPrioridade ),
                shape = ShapeDefaults.ExtraLarge,
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(cardPrioridade) {
//                        start.linkTo(stringPrioridade.end, margin = 10.dp)
                        end.linkTo(buttonDeleta.start, margin = 15.dp)
                        top.linkTo(stringDescricao.bottom, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    }
            ){}
            IconButton(
                onClick = {
                        deleteDialog()
                },
                colors = IconButtonDefaults.iconButtonColors( containerColor = WHITE ),
                modifier = Modifier.constrainAs(buttonDeleta){
                    top.linkTo(stringDescricao.bottom, margin = 10.dp)
//                    start.linkTo(cardPrioridade.end, margin = 30.dp)
                    end.linkTo(checkoxTarefaConcluida.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
                ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_button),
                    contentDescription = "Botão de deletar, uma lata de lixo vermelha",
                )
            }

            Checkbox(
                checked = isChecked!!,
                onCheckedChange = {
                    isChecked = it

                    scope.launch(Dispatchers.IO){
                        if(isChecked!!){
                            tarefasViewModel
                                .atualizaCheckBoxTarefa(
                                    tituloDaTarefa.toString(), true)
                        } else {
                            tarefasViewModel
                                .atualizaCheckBoxTarefa(
                                    tituloDaTarefa.toString(), false)
                        }
                    }
                },
                modifier = Modifier.constrainAs(checkoxTarefaConcluida){
//                    start.linkTo(buttonDeleta.end, )
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(stringDescricao.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = GREEN_SELECTED_RADIO,
                    uncheckedColor = Color.Black
                )
            )

        }
    }
}
