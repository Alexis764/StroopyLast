package com.project.stroopylast.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserScoreEntity::class], version = 1)
abstract class RankingDatabase : RoomDatabase() {

    abstract fun getUserScoreDao(): UserScoreDao

}