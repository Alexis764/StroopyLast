package com.project.stroopylast.feature.nickname

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NickNameViewModel @Inject constructor(
    avatarProvider: AvatarProvider
) : ViewModel() {

    private val _avatarList = mutableStateListOf<AvatarModel>()
    val avatarList: List<AvatarModel> = _avatarList

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    fun onNickNameChange(value: String) {
        if (value.length <= 20) _nickName.value = value
    }

    fun onAvatarSelectedChanged(avatarModel: AvatarModel) {
        val avatarIndex = _avatarList.indexOf(avatarModel)

        _avatarList.forEachIndexed { index, _ ->
            _avatarList[index] = _avatarList[index].copy(isSelected = false)
        }

        _avatarList[avatarIndex] = _avatarList[avatarIndex].copy(isSelected = true)
    }

    init {
        _avatarList.addAll(avatarProvider())
    }

}
