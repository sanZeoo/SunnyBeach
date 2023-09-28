package com.sanZeoo.sunnybeach.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.theme.MenuFontColor
import com.sanZeoo.sunnybeach.theme.MenuLikeFontColor

@Composable
fun BottomBehaviorView(
    commentPic:Int = R.mipmap.ic_moment_comment,
    commentCount : Int,
    thumbUpCount : Int,
    hasThumbUp:Boolean = false
) {
    Row(modifier = Modifier.border(0.5.dp, color = Color.White)) {

        Row(
            modifier = Modifier.weight(1.0f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.share_ic),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(10.dp),
                colorFilter = ColorFilter.tint(MenuFontColor)
            )
            Text(text = "分享", color = MenuFontColor)
        }
        val commentCountText =
            if (commentCount == 0) "评论" else commentCount.toString()
        Row(
            modifier = Modifier.weight(1.0f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = commentPic),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(6.dp)
            )
            Text(text = commentCountText, color = MenuFontColor)
        }
        val greatCount =
            if (thumbUpCount == 0) "点赞" else thumbUpCount.toString()
        val fontColor =
            if (hasThumbUp) MenuLikeFontColor else MenuFontColor
        Row(
            modifier = Modifier.weight(1.0f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = R.mipmap.ic_great_normal),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(10.dp),
                colorFilter = ColorFilter.tint(fontColor)
            )
            Text(text = greatCount, color = MenuFontColor)
        }

    }
}