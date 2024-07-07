@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.project.stroopylast.feature.nickname

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.stroopylast.navigation.Routes
import com.project.stroopylast.ui.components.MyAvatar
import com.project.stroopylast.ui.components.MyLogoImage
import com.project.stroopylast.ui.components.MyNameAppText
import com.project.stroopylast.ui.theme.GrayCard
import com.project.stroopylast.ui.theme.GreenGame

@Composable
fun SharedTransitionScope.NickNameScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavHostController = rememberNavController(),
    nickNameViewModel: NickNameViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NickNameTopBar()
        UserOptionsSection(animatedVisibilityScope, navController, nickNameViewModel)
    }
}


@Composable
fun SharedTransitionScope.UserOptionsSection(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavHostController,
    nickNameViewModel: NickNameViewModel
) {
    val context = LocalContext.current
    val avatarList = nickNameViewModel.avatarList
    val nickName by nickNameViewModel.nickName.observeAsState("")

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
            UserOptionsTitle()
            AvatarList(
                avatarList,
                animatedVisibilityScope
            ) { nickNameViewModel.onAvatarSelectedChanged(it) }
            Divider()

            Spacer(modifier = Modifier.height(30.dp))
            NickNameTextField(nickName) { nickNameViewModel.onNickNameChange(it) }
            Spacer(modifier = Modifier.height(30.dp))

            ContinueButton(Modifier.weight(1f)) {
                if (nickName.trim().isNotBlank()) {
                    navController.navigate(
                        Routes.GameScreen.createRoute(
                            nickName,
                            avatarList.first { it.isSelected }.image
                        )
                    ) {
                        popUpTo(Routes.NickNameScreen.route) {
                            inclusive = true
                        }
                    }

                } else {
                    Toast.makeText(context, "NickName empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun ContinueButton(modifier: Modifier = Modifier, onContinueClick: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(80.dp)
                .clickable { onContinueClick() },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            border = BorderStroke(3.dp, GreenGame)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = GreenGame,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Composable
fun NickNameTextField(nickName: String, onValueChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "NickName",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nickName,
            onValueChange = { onValueChange(it) },
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
            maxLines = 1,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Gray
            )
        )
    }
}

@Composable
fun SharedTransitionScope.AvatarList(
    avatarList: List<AvatarModel>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onAvatarSelectedChanged: (AvatarModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        items(avatarList) {
            MyAvatar(image = it.image, isSelected = it.isSelected, animatedVisibilityScope) {
                onAvatarSelectedChanged(it)
            }
        }
    }
}

@Composable
fun UserOptionsTitle() {
    Text(
        text = "Choose your avatar",
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    )
}


@Composable
fun NickNameTopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(GreenGame)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyLogoImage(Modifier.size(50.dp))
        Spacer(modifier = Modifier.width(12.dp))
        MyNameAppText(fontSize = 32.sp)
    }
}