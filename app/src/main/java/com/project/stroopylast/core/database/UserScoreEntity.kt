package com.project.stroopylast.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserScoreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,

    @ColumnInfo("image") val image: Int,
    @ColumnInfo("userName") val userName: String,
    @ColumnInfo("score") val score: Int
)
