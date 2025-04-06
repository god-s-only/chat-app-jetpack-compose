package com.example.chattingapp.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chattingapp.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val firebaseDatabase = Firebase.database
    private var _state = MutableStateFlow<List<Channel>>(emptyList())
    var state = _state.asStateFlow()
    init {
        getChannels()
    }
    private fun getChannels(){
        firebaseDatabase.getReference("channel").get().addOnSuccessListener {
            val list = mutableListOf<Channel>()
            it.children.forEach { data ->
                val channel = Channel(data.key!!, data.value.toString())
                list.add(channel)
            }
            _state.value = list
        }
    }
}
