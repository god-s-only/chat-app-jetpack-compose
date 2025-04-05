package com.example.chattingapp.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController){
    Scaffold {
        Column(modifier = Modifier.padding(it).padding(16.dp).fillMaxSize()) {

        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    HomeScreen(navController = rememberNavController())
}