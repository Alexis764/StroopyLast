package com.project.stroopylast.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.stroopylast.R
import com.project.stroopylast.ui.theme.GreenGame

@Composable
fun MyLogoImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = modifier
    )
}


@Composable
fun MyNameAppText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp,
    color: Color = Color.White
) {
    Text(
        text = "Stroopy",
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        modifier = modifier
    )
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MyAvatar(
    image: Int,
    isSelected: Boolean = false,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onAvatarClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Card(
            shape = CircleShape,
            border = if (isSelected) BorderStroke(6.dp, GreenGame) else null,
            modifier = Modifier
                .size(70.dp)
                .clickable { onAvatarClick() },
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/$image"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ -> tween(500) }
                    )
            )
        }
    }
}


@Composable
fun BasicUserTopBar(
    image: Int,
    nickName: String,
    modifier: Modifier = Modifier,
    complement: @Composable (Modifier) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
    ) {
        Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            MyLogoImage(Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(8.dp))
            MyNameAppText(color = Color.Black)
        }

        complement(Modifier.align(Alignment.Bottom))

        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = modifier.size(60.dp)
                )
                Text(
                    text = if (nickName.length <= 10) nickName else nickName.substring(0, 10),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}