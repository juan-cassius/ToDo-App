package com.juancassius.todolist.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.juancassius.todolist.R
import com.juancassius.todolist.components.CustomAuthButton
import com.juancassius.todolist.listener.AuthListener
import com.juancassius.todolist.ui.theme.DARK_BLUE
import com.juancassius.todolist.ui.theme.DARK_PINK
import com.juancassius.todolist.ui.theme.Purple700
import com.juancassius.todolist.ui.theme.WHITE
import com.juancassius.todolist.viewmodels.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavController,
    authViewModel: AuthViewModel
){

    val context = LocalContext.current
    var currentUser = authViewModel.verifyLogin().collectAsState(initial = false).value

    LaunchedEffect(currentUser){
        if(currentUser){
            navController.navigate("listaDeTarefas")
        } else{
            currentUser = false
        }
    }
    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var afterClickMessage by remember {
        mutableStateOf("")
    }

    val visibilityIcon =
        if(isPasswordVisible)
            painterResource(id = R.drawable.baseline_visibility_off)
        else
            painterResource(id = R.drawable.baseline_visibility)

    Scaffold(
        modifier = Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    DARK_PINK, DARK_BLUE
                )
            )
        ),
          containerColor = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_task),
                contentDescription = "Ícone de tarefa",
                modifier = Modifier.size(100.dp),
                tint = WHITE
            )
            OutlinedTextField(
                value = email,
                onValueChange ={
                    email = it
                    afterClickMessage = ""
                },
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = WHITE,
                    textColor = Color.Black,
                    cursorColor = Purple700,
                    focusedBorderColor = Purple700,
                    focusedLabelColor = Purple700
                ),
                shape = ShapeDefaults.Small,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_email),
                        contentDescription = "Ícone de email"
                    )
                },
                maxLines = 1
            )
            OutlinedTextField(
                value = senha,
                onValueChange ={
                    senha = it
                    afterClickMessage = ""
                },
                label = {
                    Text(text = "Senha")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = WHITE,
                    textColor = Color.Black,
                    cursorColor = Purple700,
                    focusedBorderColor = Purple700,
                    focusedLabelColor = Purple700
                ),
                shape = ShapeDefaults.Small,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                trailingIcon = {
                    
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }) {

                        Icon(
                            painter = visibilityIcon,
                            contentDescription = "Ícone de esconder / mostrar senha"
                        )

                    }
                },
                maxLines = 1,
                visualTransformation =
                    if(isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation()

            )
            Text(
                text = afterClickMessage,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = WHITE
            )
            CustomAuthButton(
                {
                    authViewModel.login(email, senha, object : AuthListener {
                        override fun onSuccess(message: String, viewPage: String) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            navController.navigate(viewPage)
                        }

                        override fun onFailure(error: String) {
                            afterClickMessage = error
                        }

                    } )
                },
                "Login")
            Spacer(modifier = Modifier.padding(20.dp))
            TextButton(
                onClick = {
                          navController.navigate("cadastro")
                },
            ){
                Text(
                    text = "Não tem conta? cadastre-se agora!",
                    color = WHITE,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
        }
    }

}