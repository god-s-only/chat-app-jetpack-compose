package com.example.chattingapp.feature.auth.signup

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chattingapp.R

@Composable
fun SignUpScreen(navController: NavController){
    var fullName by remember{ mutableStateOf("")}
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("")}
    var cPassword by remember{ mutableStateOf("")}

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
            OutlinedTextField(value = fullName, onValueChange = {fullName = it}, label = {Text(text = "Full Name")}, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = email, onValueChange = {email = it}, label = {Text(text = "Email")}, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = password, onValueChange = {password = it}, visualTransformation = PasswordVisualTransformation(),label = {Text(text = "Password")}, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = cPassword, onValueChange = {cPassword = it}, visualTransformation = PasswordVisualTransformation(),label = {Text(text = "Confirm Password")}, isError = password != cPassword && password.isEmpty() && cPassword.isEmpty() ,modifier = Modifier.fillMaxWidth())
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = password.isNotEmpty() && cPassword.isNotEmpty() && email.isNotEmpty() && fullName.isNotEmpty() && password == cPassword
            ) {
                Text(text = "Sign Up")
            }
            TextButton(onClick = {navController.popBackStack()}) {
                Text(text = "Already have an account? Sign In", color = Color.Gray)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    SignUpScreen(navController = rememberNavController())
}