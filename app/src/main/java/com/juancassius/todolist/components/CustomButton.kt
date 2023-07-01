package com.juancassius.todolist.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.juancassius.todolist.ui.theme.LIGHT_BLUE
import com.juancassius.todolist.ui.theme.WHITE

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = LIGHT_BLUE,
            contentColor = WHITE
        ),
        modifier = modifier,
        shape = ShapeDefaults.Small
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun CustomButtonPreview(){
//    CustomButton()
}