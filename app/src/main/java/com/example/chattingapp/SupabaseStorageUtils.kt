package com.example.chattingapp

import android.content.Context
import android.net.Uri
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.util.UUID

class SupabaseStorageUtils(var context: Context) {
    val supabase = createSupabaseClient(
        "https://hfkrqaxjloefepckjuft.supabase.co",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imhma3JxYXhqbG9lZmVwY2tqdWZ0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDM4NTM2OTQsImV4cCI6MjA1OTQyOTY5NH0.c35fAJ2RKHup2zCU1uoohmVT8oItfTLNc1GKjRGwZk4"
    ){
        install(Storage)
    }

    suspend fun uploadImage(uri: Uri): String? {
        try {
            val extension = uri.path?.substringAfterLast(".") ?: "jpg"
            val fileName = "${UUID.randomUUID()}.$extension"
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            supabase.storage.from(BUCKET_NAME).upload(fileName, inputStream.readBytes())
            val publicUrl = supabase.storage.from(BUCKET_NAME).publicUrl(fileName)
            return publicUrl
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

    companion object{
        const val BUCKET_NAME = "chat.app.images"
    }
}