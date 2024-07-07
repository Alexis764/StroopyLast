package com.project.stroopylast.feature.ranking

import com.project.stroopylast.core.database.UserScoreDao
import com.project.stroopylast.core.database.UserScoreEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RankingRepository @Inject constructor(
    private val userScoreDao: UserScoreDao
) {

    fun getAllUserScore(): Flow<List<UserScoreEntity>> {
        return userScoreDao.getAllUserScore()
    }

}