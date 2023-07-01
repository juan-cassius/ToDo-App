package com.juancassius.todolist.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juancassius.todolist.ui.theme.DARK_BLUE
import com.juancassius.todolist.ui.theme.DARK_PINK
import com.juancassius.todolist.ui.theme.WHITE

@Composable
fun CustomAuthButton(
    onClick: () -> Unit = {},
    buttonText: String = "Pressionar"
){
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .padding(20.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        DARK_PINK, DARK_BLUE
                    )
                ),
                shape = ShapeDefaults.Large
            ),
        elevation = ButtonDefaults.elevatedButtonElevation(0.dp,0.dp,0.dp,0.dp,0.dp),
        shape = ShapeDefaults.Large,
        border = BorderStroke(
            width = 2.dp,
            color = WHITE
        )
//      Caso seja necessário é só descomentar as linhas abaixo
//      colors = ButtonDefaults.outlinedButtonColors(
//          containerColor = Color.Transparent
//      )
    ){
        Text(
            text = buttonText,
            fontWeight = FontWeight.Bold,
            color = WHITE,
            fontSize = 18.sp,
        )
    }
}

@Preview
@Composable
fun CustomAuthButtonPreview(){
    CustomAuthButton()
}