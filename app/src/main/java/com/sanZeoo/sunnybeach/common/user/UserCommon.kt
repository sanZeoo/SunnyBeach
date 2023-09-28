package com.sanZeoo.sunnybeach.common.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sanZeoo.sunnybeach.ktx.getFriendlyTimeSpanByNow
import com.sanZeoo.sunnybeach.ktx.ifNullOrEmpty
import com.sanZeoo.sunnybeach.theme.DefaultFontColor
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun UserView(
    avatar: String,
    name: String,
    imageSize: Int = 40,
    textSize: Int = 14,
    fontWeight: FontWeight = FontWeight.Normal,
    positionView: @Composable () -> Unit = {},
) {
    Row(modifier = Modifier.padding(vertical = 10.dp)) {
        AsyncImage(
            model = avatar, contentDescription = "头像",
            modifier = Modifier
                .size(imageSize.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = name,
                fontSize = textSize.sp,
                fontWeight = fontWeight,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            positionView.invoke()
        }
    }
}

@Composable
fun UserDetailView(
    avatar: String,
    title: String,
    leftSubtitle: String?,
    rightSubtitle: String,
    imageSize: Int = 40,
    titleTextSize: Int = 14,
) {
    val mSdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE)

    UserView(avatar, title, imageSize, titleTextSize, fontWeight = FontWeight.Bold) {
        Text(
            text = "${leftSubtitle.ifNullOrEmpty { "摊友" }} · ${
                rightSubtitle.getFriendlyTimeSpanByNow(mSdf)
            }",
            fontSize = 11.sp,
            color = DefaultFontColor
        )
    }
}

