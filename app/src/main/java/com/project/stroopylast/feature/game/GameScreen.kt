@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.project.stroopylast.feature.game

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.stroopylast.navigation.Routes
import com.project.stroopylast.ui.components.BasicUserTopBar
import com.project.stroopylast.ui.theme.BlueGame
import com.project.stroopylast.ui.theme.GrayCard
import com.project.stroopylast.ui.theme.GreenGame
import com.project.stroopylast.ui.theme.RedGame


@Composable
fun SharedTransitionScope.GameScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    userName: String,
    userImage: Int,
    navController: NavHostController = rememberNavController(),
    gameViewModel: GameViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        gameViewModel.initUserInfo(userImage, userName)
    }

    val startNextScreen by gameViewModel.startNextScreen.observeAsState(false)

    if (startNextScreen) {
        navController.navigate(Routes.RankingScreen.createRoute(userName, userImage)) {
            popUpTo(Routes.GameScreen.route) {
                inclusive = true
            }
        }
    }

    val score by gameViewModel.score.observeAsState(0)

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BasicUserTopBar(
            image = userImage,
            nickName = userName,
            modifier = Modifier.sharedElement(
                state = rememberSharedContentState(key = "image/$userImage"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ -> tween(500) }
            )
        ) {
            Text(
                text = "Score: $score",
                modifier = it,
                color = GreenGame,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        GameButtons(gameViewModel, score, userImage, userName)
    }
}


@Composable
fun GameButtons(gameViewModel: GameViewModel, score: Int, userImage: Int, userName: String) {
    val word by gameViewModel.word.observeAsState(WordClass.Blue)
    val wordColor by gameViewModel.wordColor.observeAsState(RedGame)
    val colorsButtonList = gameViewModel.colorsButtonList
    val correctColor by gameViewModel.correctColor.observeAsState(BlueGame)
    val incorrectAnswer by gameViewModel.incorrectAnswer.observeAsState(false)
    val progressValue by gameViewModel.progressValue.observeAsState(1f)

    Card(
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = GrayCard),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameWord(word.name, wordColor)

            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround,
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(colorsButtonList) {
                    ColorButton(it, incorrectAnswer, progressValue) { colorClicked ->
                        if (colorClicked == correctColor) {
                            gameViewModel.plusScore()
                            gameViewModel.changeGameOptions()

                        } else {
                            gameViewModel.wordTimer.cancel()
                            gameViewModel.gameFinish(userImage, userName, score)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorButton(
    color: Color,
    incorrectAnswer: Boolean,
    progressValue: Float,
    onButtonClick: (Color) -> Unit
) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = if (!incorrectAnswer) color else Color.Gray),
        modifier = Modifier
            .size(120.dp)
            .clickable { if (!incorrectAnswer) onButtonClick(color) },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = if (!incorrectAnswer) color else Color.Gray),
                modifier = Modifier.size(90.dp),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                CircularProgressIndicator(
                    progress = progressValue,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(180f)
                )
            }
        }
    }
}

@Composable
fun GameWord(word: String, color: Color) {
    Text(
        text = word.uppercase(),
        color = color,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 46.sp,
        letterSpacing = 0.2.em
    )
}