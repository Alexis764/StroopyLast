package com.project.stroopylast.navigation

sealed class Routes(val route: String) {

    data object SplashScreen : Routes("splashScreen")

    data object NickNameScreen : Routes("nickNameScreen")

    data object GameScreen : Routes("gameScreen/{userName}/{userImage}") {
        fun createRoute(userName: String, userImage: Int) = "gameScreen/$userName/$userImage"
    }

    data object RankingScreen : Routes("rankingScreen/{userName}/{userImage}") {
        fun createRoute(userName: String, userImage: Int) = "rankingScreen/$userName/$userImage"
    }

}