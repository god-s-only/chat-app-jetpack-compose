package com.example.chattingapp.model

data class Channel(
    var id: String = "",
    var name: String,
    var createdAt: Long = System.currentTimeMillis()
)
