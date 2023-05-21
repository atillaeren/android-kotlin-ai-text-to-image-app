package com.example.aiartcreator.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.flow.*
import androidx.lifecycle.ViewModel
import com.example.aiartcreator.repository.Repository
import com.example.aiartcreator.Result
import com.example.aiartcreator.daoModel.RoomEntity
import com.example.aiartcreator.data.remote.ApiService

class AppViewModel(private val apiService: ApiService, private val repository: Repository) : ViewModel() {

    val text2imageState = MutableStateFlow<Result<Bitmap>>(Result.Idle("asdas"))

    suspend fun getTextToImage(text: String, category: String) {
        try {
            val response = apiService.getTextToImage(
                "your_api_key_here",
                "$text, $category",
                7,
                30,
                1,
                1024,
                768,
                "dpm"
            )

            if (response.isSuccessful) {
                val byteArray = response.body()?.byteStream()?.readBytes()
                val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }
                text2imageState.emit(Result.Success(bitmap))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                text2imageState.emit(Result.Error(errorMessage, null))
            }
        } catch (e: Exception) {
            // text2imageState.emit(Result.Error("Network error: ${e.message}"))
        }
    }

    val allImageList: Flow<List<RoomEntity>> = repository.allImages

    suspend fun insertImage(roomEntity: RoomEntity) {
        repository.insertImage(roomEntity)
    }
}
