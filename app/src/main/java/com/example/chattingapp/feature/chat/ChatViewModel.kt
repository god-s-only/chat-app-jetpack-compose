package com.example.chattingapp.feature.chat

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chattingapp.SupabaseStorageUtils
import com.example.chattingapp.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel(){
    private val _message = MutableStateFlow<List<Message>>(emptyList())
    var message = _message.asStateFlow()
    var database = Firebase.database

    fun sendMessage(channelID: String, message: String?, image: String? = null){
        val message = Message(
            id = database.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = Firebase.auth.currentUser?.uid ?: "",
            message = message,
            System.currentTimeMillis(),
            Firebase.auth.currentUser?.displayName,
            senderImage = null,
            imageUrl = image
        )
        database.reference.child("messages").child(channelID).push().setValue(message)
    }

    fun sendImageMessage(uri: Uri, channelID: String){
        viewModelScope.launch {
            val storageUtils = SupabaseStorageUtils(context)
            val downloadUri = storageUtils.uploadImage(uri)
            downloadUri?.let {
                sendMessage(channelID, null, downloadUri)
            }
        }
    }

    fun listenForMessages(channelID: String){
        database.getReference("messages").child(channelID).orderByChild("createdAt")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        message?.let{
                            list.add(message)
                        }
                    }
                    _message.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}