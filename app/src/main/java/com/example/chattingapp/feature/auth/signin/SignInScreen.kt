package com.example.chattingapp.feature.auth.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.chattingapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignInScreen(navController: NavController){
    var viewModel: SignInViewModel = hiltViewModel()
    var uiState = viewModel.state.collectAsState()
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("")}
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value){
            is SignInState.Success -> {
                navController.navigate("homescreen")
                Toast.makeText(context, "Sign in successfully", Toast.LENGTH_LONG).show()
            }
            is SignInState.Error -> {
                Toast.makeText(context, "Sign in failed", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.size(200.dp)
                    .background(
                        Color.White
                    )
            )
            OutlinedTextField(value = email, onValueChange = {email = it}, label = {Text(text = "Email")}, modifier = Modifier.fillMaxWidth())

            OutlinedTextField(value = password, onValueChange = {password = it}, visualTransformation = PasswordVisualTransformation(),label = {Text(text = "Password")}, modifier = Modifier.fillMaxWidth())
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            if(uiState.value == SignInState.Loading){
                CircularProgressIndicator()
            }else{
                Button(
                    onClick = {viewModel.signIn(email, password)},
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotEmpty() && password.isNotEmpty() && (uiState.value == SignInState.Nothing || uiState.value == SignInState.Error)
                ) {
                    Text(text = "Sign In")
                }
                TextButton(onClick = {navController.navigate("signup")}) {
                    Text(text = "Don't have an account? Sign Up", color = Color.Gray)
                }
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    SignInScreen(navController = rememberNavController())
}