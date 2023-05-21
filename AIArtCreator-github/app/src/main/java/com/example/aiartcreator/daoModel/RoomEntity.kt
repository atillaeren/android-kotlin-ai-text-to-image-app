package com.example.aiartcreator.daoModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "image")
    var image : ByteArray,

    @ColumnInfo(name = "description")
    var description : String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoomEntity

        if (id != other.id) return false
        if (!image.contentEquals(other.image)) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + image.contentHashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}
