package com.project.stroopylast.feature.game

import com.project.stroopylast.core.database.UserScoreDao
import com.project.stroopylast.core.database.UserScoreEntity
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val userScoreDao: UserScoreDao
) {

    suspend fun insertUserScore(userImage: Int, userName: String, score: Int) {
        userScoreDao.insertUserScore(
            UserScoreEntity(image = userImage, userName = userName, score = score)
        )
    }

}