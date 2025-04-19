package com.example.chattingapp.feature.chat

import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chattingapp.model.Message
import com.example.chattingapp.ui.theme.DarkGray
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.chattingapp.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatScreen(navController: NavController, channelId: String){
    var viewModel: ChatViewModel = hiltViewModel()
    Scaffold(containerColor = DarkGray) {
        val chooserDialog = remember {
            mutableStateOf(false)
        }
        val cameraImageUri = remember {
            mutableStateOf<Uri?>(null)
        }

        val cameraImageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            if(success){
                cameraImageUri.value?.let {
                    viewModel.sendImageMessage(it, channelId)
                }
            }
        }

        val imageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
            uri?.let {
                viewModel.sendImageMessage(it, channelId)
            }
        }

        fun createImageUri(): Uri {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val storageDir = ContextCompat.getExternalFilesDirs(
                navController.context,
                Environment.DIRECTORY_PICTURES
            ).first()
            return FileProvider.getUriForFile(
                navController.context,
                "${navController.context.packageName}.provider",
                File.createTempFile(
                    "JPEG_${timeStamp}_", ".jpg", storageDir
                ).apply {
                    cameraImageUri.value = Uri.fromFile(this)
                }
            )
        }
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if(isGranted){
                cameraImageLauncher.launch(createImageUri())
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            LaunchedEffect(key1 = true) {
                viewModel.listenForMessages(channelId)
            }
            val messages = viewModel.message.collectAsState()
            ChatMessages(
                messages.value,
                onSendMessage = {
                    viewModel.sendMessage(channelId, it)
                }
            ){
                chooserDialog.value = true
            }
        }


        if(chooserDialog.value){
            ContentSelectionDialog(onCameraSelected = {
                chooserDialog.value = false
                if(navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED){
                    cameraImageLauncher.launch(createImageUri())
                }else{
                    chooserDialog.value = false
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }, onGallerySelected = {
                chooserDialog.value = false
                imageLauncher.launch("image/*")
            })
        }
    }


}

@Composable
fun ContentSelectionDialog(onCameraSelected : () -> Unit, onGallerySelected : () -> Unit){
    AlertDialog(onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = onCameraSelected) {
                Text(text = "Camera", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onGallerySelected) {
                Text(text = "Gallery", color = Color.White)
            }
        },
        title = {Text(text = "Select your source")},
        text = {
            Text(text = "Would you like to take a picture or select from the Gallery?", color = Color.White)
        })
}

@Composable
fun ChatMessages(messages: List<Message>, onSendMessage : (String) -> Unit, onImageClicked : () -> Unit){
    var hideKeyboardController = LocalSoftwareKeyboardController.current
    var message by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        LazyColumn {
            items(messages) { message ->
                ChatBubble(message)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGray)
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
           IconButton(onClick = onImageClicked) {
               Image(painter = painterResource(id = R.drawable.baseline_attach_file_24), contentDescription = "Attach")
           }
            OutlinedTextField(
                value = message,
                onValueChange = {message = it},
                placeholder = {
                    Text(
                    text = "Write your message"
                )},
                colors = TextFieldDefaults.colors().copy(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = DarkGray,
                    unfocusedContainerColor = DarkGray,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White
                ),
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboardController?.hide()
                    }
                )
            )
            IconButton(onClick = {if (message.isNotEmpty()){
                onSendMessage(message)
                message = ""
            }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.Blue)
            }
        }
    }
}
@Composable
fun ChatBubble(message: Message){
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if(isCurrentUser) Color.Blue else Color.Green

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),

    ) {
        val alignment = if(isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
        Box(
             modifier = Modifier
                 .padding(8.dp)
                 .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                 .align(alignment)
        ){
            if(message.imageUrl != null){
                AsyncImage(
                    model = message.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }else{
                Text(
                    text = message.message?: "",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){

}