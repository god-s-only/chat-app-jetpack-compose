package com.example.chattingapp.feature.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ChatScreen(navController: NavController, channelId: String){
    var viewModel: ChatViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {

    }
}