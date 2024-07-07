package com.project.stroopylast.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserScoreDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserScore(userScoreEntity: UserScoreEntity)

    @Query("SELECT * FROM UserScoreEntity")
    fun getAllUserScore(): Flow<List<UserScoreEntity>>

}