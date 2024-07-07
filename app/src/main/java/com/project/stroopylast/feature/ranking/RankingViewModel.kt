package com.project.stroopylast.feature.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stroopylast.core.database.UserScoreEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankingRepository: RankingRepository
) : ViewModel() {

    private val _rankingList = mutableListOf<UserScoreEntity>()
    val rankingList: List<UserScoreEntity> = _rankingList

    init {
        viewModelScope.launch {
            rankingRepository.getAllUserScore().collect {
                _rankingList.clear()

                val sortList = it.sortedByDescending { userScoreEntity -> userScoreEntity.score }

                _rankingList.addAll(
                    if (sortList.size > 9) sortList.subList(0, 9) else sortList
                )
            }
        }
    }

}