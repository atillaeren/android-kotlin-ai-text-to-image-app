package com.example.aiartcreator.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aiartcreator.daoModel.RoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface ImageDAO {

    @Query("SELECT * FROM image_table")
    fun getAllImageList(): Flow<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(userEntity: RoomEntity)
}