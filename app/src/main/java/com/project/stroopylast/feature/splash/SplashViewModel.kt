package com.project.stroopylast.feature.splash

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _startNextScreen = MutableLiveData<Boolean>()
    val startNextScreen: LiveData<Boolean> = _startNextScreen

    private val _seconds = MutableLiveData<Int>()
    val seconds: LiveData<Int> = _seconds

    private val splashTimer = object : CountDownTimer(3000, 1000) {
        override fun onTick(milliseconds: Long) {
            _seconds.value = (milliseconds / 1000).toInt()
        }

        override fun onFinish() {
            _startNextScreen.value = true
        }
    }.start()

}