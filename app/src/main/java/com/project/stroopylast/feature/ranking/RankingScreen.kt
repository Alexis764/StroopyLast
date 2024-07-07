package com.project.stroopylast.feature.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.stroopylast.core.database.UserScoreEntity
import com.project.stroopylast.navigation.Routes
import com.project.stroopylast.ui.components.BasicUserTopBar
import com.project.stroopylast.ui.theme.BlueGame
import com.project.stroopylast.ui.theme.GreenGame
import com.project.stroopylast.ui.theme.YellowCard

@Composable
fun RankingScreen(
    userName: String,
    userImage: Int,
    navController: NavHostController = rememberNavController(),
    rankingViewModel: RankingViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BasicUserTopBar(image = userImage, nickName = userName) {}

        RankingList(navController, rankingViewModel, Modifier.weight(1f))

        RankingBottomBar(
            onPlayAgainButtonClick = {
                navController.navigate(Routes.GameScreen.createRoute(userName, userImage)) {
                    popUpTo(Routes.RankingScreen.route) {
                        inclusive = true
                    }
                }
            },
            onNewPlayerButtonClick = {
                navController.navigate(Routes.NickNameScreen.route) {
                    popUpTo(Routes.RankingScreen.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}


@Composable
fun RankingBottomBar(onPlayAgainButtonClick: () -> Unit, onNewPlayerButtonClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Select a player from the top",
            color = GreenGame,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Text(
            text = "or",
            color = Color.LightGray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { onPlayAgainButtonClick() },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueGame),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Play again")
            }

            Button(
                onClick = { onNewPlayerButtonClick() },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenGame),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "New player")
            }
        }
    }
}


@Composable
fun RankingList(
    navController: NavHostController,
    rankingViewModel: RankingViewModel,
    modifier: Modifier = Modifier
) {
    val rankingList = rankingViewModel.rankingList

    Card(
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = YellowCard),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier.padding(20.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RankingTitle()

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(rankingList) { index, userScore ->
                    val isLastElement = (index + 1) == rankingList.size
                    UserScoreItem(isLastElement, userScore) {
                        navController.navigate(
                            Routes.GameScreen.createRoute(
                                it.userName,
                                it.image
                            )
                        ) {
                            popUpTo(Routes.RankingScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserScoreItem(
    isLastElement: Boolean,
    userScore: UserScoreEntity,
    onItemClick: (UserScoreEntity) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onItemClick(userScore) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = userScore.image),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = userScore.userName,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Score: ${userScore.score}",
                    color = GreenGame,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (!isLastElement) Divider()
    }
}

@Composable
fun RankingTitle() {
    Text(
        text = "Ranking",
        color = GreenGame,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 46.sp
    )
}