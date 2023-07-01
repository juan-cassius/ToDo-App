package com.juancassius.todolist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.juancassius.todolist.ui.theme.BLACK
import com.juancassius.todolist.ui.theme.LIGHT_BLUE
import com.juancassius.todolist.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier,
    labelText: String,
    maxLines: Int = 1,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        label = {
            Text(labelText)
        },
        maxLines = maxLines,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = LIGHT_BLUE,
            unfocusedBorderColor = Color.Gray,
            textColor = BLACK,
            focusedLabelColor = LIGHT_BLUE,
            containerColor = WHITE,
            cursorColor = LIGHT_BLUE
        ),
        shape = ShapeDefaults.Large
    )
}

@Composable
@Preview
fun CustomTextFieldPreview(){
    CustomTextField(value = "Valor Teste",
        onChange = {},
        modifier = Modifier.fillMaxWidth(),
        labelText = "Título da Tarefa",
        keyboardType = KeyboardType.Text,
    )
}