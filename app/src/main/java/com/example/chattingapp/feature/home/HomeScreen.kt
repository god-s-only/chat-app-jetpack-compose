package com.example.chattingapp.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController){
    var homeViewView = hiltViewModel<HomeViewModel>()
    var channels = homeViewView.state.collectAsState()
    Scaffold {
        Box(
            modifier = Modifier.padding(it).fillMaxSize()
        ){
            LazyColumn {
                items(channels.value) { channel ->
                    Column {
                        Text(text = channel.name)
                    }
                }
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    HomeScreen(navController = rememberNavController())
}