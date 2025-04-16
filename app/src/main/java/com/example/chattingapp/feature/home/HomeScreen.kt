package com.example.chattingapp.feature.home

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chattingapp.model.Channel
import com.example.chattingapp.ui.theme.DarkGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    var homeViewModel = hiltViewModel<HomeViewModel>()
    var channels = homeViewModel.state.collectAsState()
    var addChannel = remember {
        mutableStateOf(false)
    }
    Scaffold(floatingActionButton = {
        Box(
            modifier = Modifier.padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Blue)
                .clickable{
                    addChannel.value = true
                }
        ){
            Text(text = "Add Channel", modifier = Modifier.padding(16.dp), color = Color.White)
        }
    },
        containerColor = Color.Black) {
        Box(
            modifier = Modifier.padding(it).fillMaxSize()
        ){
            LazyColumn {
                item {
                    Text(text = "Messages", color = Color.Gray, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Black), modifier = Modifier.padding(16.dp))
                }
                item {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = {Text(text = "Search...")},
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(
                                RoundedCornerShape(40.dp)
                            ),
                        textStyle = TextStyle(color = Color.LightGray),
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = DarkGray,
                            unfocusedContainerColor = DarkGray,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedIndicatorColor = Color.Gray
                        ),
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                        }
                    )
                }
                items(channels.value) { channel ->
                    Column {
                        ChannelItems(channel.name){
                            navController.navigate("chat/${channel.id}")
                        }
                    }
                }
            }
        }
    }
    if(addChannel.value){
        ModalBottomSheet(onDismissRequest = {addChannel.value = false},
            sheetState = rememberModalBottomSheetState()
        ) {
            AddChannelName {
                homeViewModel.addChannel(it)
                addChannel.value = false
            }
        }
    }
}
@Composable
fun ChannelItems(channelName: String, onClick: () -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = DarkGray)
            .clickable{
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .clip(CircleShape)
                .size(70.dp)
                .background(color = Color.Yellow.copy(alpha = 0.3f))
        ){
            Text(text = channelName[0].toString(), color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.align(
                Alignment.Center),
                style = TextStyle(
                    fontSize = 30.sp
                ))
        }
        Text(text = channelName, color = Color.White, fontWeight = FontWeight.Bold, style = TextStyle(fontSize = 15.sp), modifier = Modifier.padding(16.dp))
    }
}
@Composable
fun AddChannelName(onChannelAdd: (String) -> Unit){
    val channelName = remember {
        mutableStateOf("")
    }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = "Add Channel Name", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(modifier = Modifier.fillMaxWidth(0.8f),
                value = channelName.value,
                onValueChange = {channelName.value = it},
                label = {Text(text = "Channel Name")},
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Button(onClick = {onChannelAdd(channelName.value)}, enabled = channelName.value.isNotEmpty()) {
                Text(text = "Add Channel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    ChannelItems("Channel Name"){}
}