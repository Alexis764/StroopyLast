package com.project.stroopylast.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.stroopylast.feature.game.GameScreen
import com.project.stroopylast.feature.nickname.NickNameScreen
import com.project.stroopylast.feature.ranking.RankingScreen
import com.project.stroopylast.feature.splash.SplashScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MyNavigation() {
    SharedTransitionLayout {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {
            composable(Routes.SplashScreen.route) {
                SplashScreen(navController)
            }

            composable(Routes.NickNameScreen.route) {
                NickNameScreen(this, navController)
            }

            composable(
                Routes.GameScreen.route,
                arguments = listOf(
                    navArgument("userName") { type = NavType.StringType },
                    navArgument("userImage") { type = NavType.IntType }
                )
            ) {
                val userName = it.arguments?.getString("userName").orEmpty()
                val userImage = it.arguments?.getInt("userImage") ?: 0

                GameScreen(this, userName, userImage, navController)
            }

            composable(
                Routes.RankingScreen.route,
                arguments = listOf(
                    navArgument("userName") { type = NavType.StringType },
                    navArgument("userImage") { type = NavType.IntType }
                )
            ) {
                val userName = it.arguments?.getString("userName").orEmpty()
                val userImage = it.arguments?.getInt("userImage") ?: 0

                RankingScreen(userName, userImage, navController)
            }
        }
    }
}