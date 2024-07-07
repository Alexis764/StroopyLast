package com.project.stroopylast.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.stroopylast.navigation.Routes
import com.project.stroopylast.ui.components.MyLogoImage
import com.project.stroopylast.ui.components.MyNameAppText
import com.project.stroopylast.ui.theme.GreenGame
import com.project.stroopylast.ui.theme.GreenGameAlpha

@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController(),
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val startNextScreen by splashViewModel.startNextScreen.observeAsState(false)
    val seconds by splashViewModel.seconds.observeAsState(3)

    if (startNextScreen) {
        navController.popBackStack()
        navController.navigate(Routes.NickNameScreen.route)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(GreenGame),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyLogoImage(Modifier.size(100.dp))
        MyNameAppText(fontSize = 40.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Reacts to colors in $seconds seconds",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
        ) {
            MyLogoImage(
                Modifier
                    .size(350.dp)
                    .offset((-150).dp, 0.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GreenGameAlpha)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    SplashScreen()
}