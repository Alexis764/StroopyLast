package com.project.stroopylast.feature.game

import android.os.CountDownTimer
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stroopylast.feature.game.WordClass.Blue
import com.project.stroopylast.feature.game.WordClass.Green
import com.project.stroopylast.feature.game.WordClass.Orange
import com.project.stroopylast.feature.game.WordClass.Purple
import com.project.stroopylast.feature.game.WordClass.Red
import com.project.stroopylast.feature.game.WordClass.Yellow
import com.project.stroopylast.ui.theme.BlueGame
import com.project.stroopylast.ui.theme.GreenGame
import com.project.stroopylast.ui.theme.OrangeGame
import com.project.stroopylast.ui.theme.PurpleGame
import com.project.stroopylast.ui.theme.RedGame
import com.project.stroopylast.ui.theme.YellowGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private var userImage: Int = 0
    private lateinit var userName: String

    fun initUserInfo(userImage: Int, userName: String) {
        this.userImage = userImage
        this.userName = userName
    }


    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    fun plusScore() {
        _score.value = _score.value?.plus(1)
    }


    private val _startNextScreen = MutableLiveData<Boolean>()
    val startNextScreen: LiveData<Boolean> = _startNextScreen

    private val _incorrectAnswer = MutableLiveData<Boolean>()
    val incorrectAnswer: LiveData<Boolean> = _incorrectAnswer

    private val _progressValue = MutableLiveData<Float>()
    val progressValue: LiveData<Float> = _progressValue

    val wordTimer = object : CountDownTimer(2000, 100) {
        override fun onTick(milliseconds: Long) {
            _progressValue.value = when {
                milliseconds >= 1500 -> 1f
                milliseconds >= 1000 -> 0.75f
                milliseconds >= 500 -> 0.50f
                else -> 0.25f
            }
        }

        override fun onFinish() {
            _progressValue.value = 0f
            gameFinish(userImage, userName, _score.value!!)
        }
    }

    private val finishGameTimer = object : CountDownTimer(2000, 1000) {
        override fun onTick(p0: Long) {}
        override fun onFinish() {
            _startNextScreen.value = true
        }
    }

    fun gameFinish(userImage: Int, userName: String, score: Int) {
        _incorrectAnswer.value = true
        finishGameTimer.start()

        viewModelScope.launch {
            gameRepository.insertUserScore(userImage, userName, score)
        }
    }


    private val wordsList = listOf(
        Blue,
        Yellow,
        Purple,
        Red,
        Green,
        Orange
    )

    private val colorList = listOf(
        BlueGame,
        YellowGame,
        PurpleGame,
        RedGame,
        GreenGame,
        OrangeGame
    )

    private val _word = MutableLiveData<WordClass>()
    val word: LiveData<WordClass> = _word

    private val _wordColor = MutableLiveData<Color>()
    val wordColor: LiveData<Color> = _wordColor

    private val _colorsButtonList = mutableListOf<Color>()
    val colorsButtonList: List<Color> = _colorsButtonList

    private val _correctColor = MutableLiveData<Color>()
    val correctColor: LiveData<Color> = _correctColor

    fun changeGameOptions() {
        _word.value = wordsList.random()

        _correctColor.value = when (_word.value) {
            Blue -> BlueGame
            Yellow -> YellowGame
            Purple -> PurpleGame
            Red -> RedGame
            Green -> GreenGame
            Orange -> OrangeGame
            null -> BlueGame
        }

        _wordColor.value = colorList.filterNot { it == _correctColor.value }.random()


        _colorsButtonList.clear()
        _colorsButtonList.add(_correctColor.value!!)
        _colorsButtonList.add(_wordColor.value!!)
        _colorsButtonList.add(
            colorList.filterNot { (it == _wordColor.value || it == _correctColor.value) }.random()
        )
        _colorsButtonList.shuffle()

        wordTimer.cancel()
        wordTimer.start()
    }


    init {
        changeGameOptions()
    }

}