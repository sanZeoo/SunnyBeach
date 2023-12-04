package com.sanZeoo.sunnybeach.common.fish

import android.text.TextUtils
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.parseAsHtml
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.ktx.getFriendlyTimeSpanByNow
import com.sanZeoo.sunnybeach.ktx.ifNullOrEmpty
import com.sanZeoo.sunnybeach.ktx.setDefaultEmojiParser
import com.sanZeoo.sunnybeach.model.fish.FishItem
import com.sanZeoo.sunnybeach.theme.ConfirmTextColor
import com.sanZeoo.sunnybeach.theme.DefaultFontColor
import com.sanZeoo.sunnybeach.theme.GreyBg
import com.sanZeoo.sunnybeach.theme.H7
import com.sanZeoo.sunnybeach.theme.LinkedTextColor
import com.sanZeoo.sunnybeach.theme.MenuFontColor
import com.sanZeoo.sunnybeach.theme.MenuLikeFontColor
import com.sanZeoo.sunnybeach.utils.emoji.EmojiImageGetter
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun FishMoment(
    onClick:() ->Unit ={},
    fishItem:FishItem,
    isExpanded:Boolean = false,
) {
    val mSdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 6.dp)
            .clickable {
                onClick.invoke()
            }
    ) {

        //用户信息
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            AsyncImage(
                model = fishItem.avatar, contentDescription = "头像",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(text = fishItem.nickname, fontSize = 18.sp)
                Text(
                    text = "${fishItem.position.ifNullOrEmpty { "摊友" }} · ${
                        fishItem.createTime.getFriendlyTimeSpanByNow(mSdf)
                    }",
                    fontSize = 11.sp,
                    color = DefaultFontColor
                )
            }
        }
//                    val parseAsHtml =

        // 内容
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                if (isExpanded){
                    it.maxLines = Int.MAX_VALUE
                    it.ellipsize = null
                }else{
                    it.maxLines = 5
                    it.ellipsize = TextUtils.TruncateAt.END
                }
                it.setTextColor(Color.Black.toArgb())
                it.setDefaultEmojiParser()
                it.text = fishItem.content.parseAsHtml(
                    imageGetter = EmojiImageGetter(it.textSize.toInt())
                )
            }
        )


        Spacer(modifier = Modifier.height(6.dp))
//                    Text(text = parseAsHtml, maxLines = 5,
//                        overflow = TextOverflow.Ellipsis,
//                        textAlign = TextAlign.Start,
//                        style = MaterialTheme.typography.bodyMedium,
//                        letterSpacing = 0.1.sp,
//                        lineHeight = 16.sp
//                    )
        val images = fishItem.images
        val imageCount = images.size
        var fixed = 1
        var fixedHeight = 1
        if (imageCount > 1) {
            fixed = if (imageCount % 2 == 0) 2 else 3
            fixedHeight =
                imageCount / fixed + if (imageCount % fixed == 0) 0 else 1
        }
        // 九宫图
        if (imageCount > 0) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(fixed), userScrollEnabled = false,
                modifier = Modifier.height(fixedHeight * 120.dp)
            ) {
                items(imageCount) { index ->
                    AsyncImage(
                        model = images[index], contentDescription = "图片",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
        // 话题标签
        val topicName = fishItem.topicName
        if (!TextUtils.isEmpty(topicName)) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = topicName, fontSize = H7, color = ConfirmTextColor,
                modifier = Modifier
                    .border(0.5.dp, ConfirmTextColor, CircleShape)
                    .padding(8.dp, 4.dp)
            )
        }
        if (!TextUtils.isEmpty(fishItem.linkUrl)) {
            Row(modifier = Modifier.background(GreyBg)) {
                AsyncImage(
                    model = fishItem.linkCover, contentDescription = "链接",
                    placeholder = rememberAsyncImagePainter(model = R.mipmap.ic_link_default),
                    error = rememberAsyncImagePainter(model = R.mipmap.ic_link_default),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = fishItem.linkTitle,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 14.sp
                    )
                    Text(
                        text = fishItem.linkUrl,
                        overflow = TextOverflow.Ellipsis,
                        color = LinkedTextColor,
                        maxLines = 1,
                        fontSize = 14.sp
                    )
                }
            }
        }
        // 底部栏 一键三连 评论 + 转发 + 点赞
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
            val commentCount =
                if (fishItem.commentCount == 0) "评论" else fishItem.commentCount.toString()
            Row(
                modifier = Modifier.weight(1.0f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.mipmap.ic_moment_comment),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(6.dp)
                )
                Text(text = commentCount, color = MenuFontColor)
            }
            val greatCount =
                if (fishItem.thumbUpCount == 0) "点赞" else fishItem.thumbUpCount.toString()
            val fontColor =
                if (fishItem.hasThumbUp) MenuLikeFontColor else MenuFontColor
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
}