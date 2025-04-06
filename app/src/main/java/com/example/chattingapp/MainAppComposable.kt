package com.example.chattingapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chattingapp.feature.auth.signin.SignInScreen
import com.example.chattingapp.feature.auth.signup.SignUpScreen
import com.example.chattingapp.feature.chat.ChatScreen
import com.example.chattingapp.feature.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp(){
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val start = if(FirebaseAuth.getInstance().currentUser != null) "homescreen" else "login"
        NavHost(navController = navController, startDestination = start){
            composable("login") {
                SignInScreen(navController)
            }
            composable("signup") {
                SignUpScreen(navController)
            }
            composable("homescreen"){
                HomeScreen(navController)
            }
        }
    }
}
