package com.example.aiartcreator.repository

import androidx.annotation.WorkerThread
import com.example.aiartcreator.daoModel.RoomEntity
import com.example.aiartcreator.data.local.ImageDAO
import kotlinx.coroutines.flow.Flow

class Repository(private val imageDAO: ImageDAO) {

    val allImages : Flow<List<RoomEntity>> = imageDAO.getAllImageList()

    @WorkerThread
    suspend fun insertImage(roomEntity: RoomEntity) {
        imageDAO.insertImage(roomEntity)
    }

}
