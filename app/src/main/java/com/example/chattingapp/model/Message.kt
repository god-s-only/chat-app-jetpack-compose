package com.example.chattingapp.model

data class Message(
    var id: String ="",
    var senderId: String = "",
    var message: String = "",
    var createdAt: Long = System.currentTimeMillis(),
    var sendName: String? = null,
    var senderImage: String? = null,
    var imageUrl: String? = null
)
