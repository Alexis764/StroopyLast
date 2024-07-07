package com.project.stroopylast.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRankingDatabase(@ApplicationContext context: Context): RankingDatabase {
        return Room.databaseBuilder(
            context,
            RankingDatabase::class.java,
            "rankingDatabase"
        ).build()
    }

    @Provides
    fun provideUserScoreDao(rankingDatabase: RankingDatabase): UserScoreDao {
        return rankingDatabase.getUserScoreDao()
    }

}