package com.example.aiartcreator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aiartcreator.daoModel.RoomEntity
import com.example.aiartcreator.data.local.ImageDAO

@Database(entities = [RoomEntity::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDAO
}