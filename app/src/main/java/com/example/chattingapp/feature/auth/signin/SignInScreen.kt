package com.example.chattingapp.feature.auth.signin

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.chattingapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignInScreen(navController: NavController){
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("")}

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
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign In")
            }
            TextButton(onClick = {}) {
                Text(text = "Don't have an account? Sign Up", color = Color.Gray)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    SignInScreen(navController = rememberNavController())
}