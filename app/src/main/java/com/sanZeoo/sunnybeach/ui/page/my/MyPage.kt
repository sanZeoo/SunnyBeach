package com.sanZeoo.sunnybeach.ui.page.my

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.common.SettingCommon
import com.sanZeoo.sunnybeach.common.user.UserBaseView
import com.sanZeoo.sunnybeach.theme.H5
import com.sanZeoo.sunnybeach.theme.H6
import com.sanZeoo.sunnybeach.ui.widget.AppToolsBgBar


@Composable
fun MyPage(navController: NavHostController) {

    val scrollState = rememberScrollState()
    Column {
        AppToolsBgBar(title = "我", contentColor = Color.White)

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                UserBaseView(
                    avatar = R.mipmap.ic_default_avatar,
                    name = "账号未登录",
                    modifier = Modifier.weight(1.0f),
                    imageModifier = Modifier.padding(30.dp),
                    imageSize = 50
                ) {
                    Text(text = "你还没有填写简介")
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_qr_code),
                    contentDescription = "扫描框",
                    modifier = Modifier.size(12.dp)
                )
                Image(
                    painter = painterResource(id = R.mipmap.ic_right),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(start = 10.dp, end = 10.dp)
                        .padding(10.dp)
                )
            }

            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_vip_banner_bg),
                    contentDescription = "开通会员",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(96.dp)
                        .clip(RoundedCornerShape(20))
                )
                Text(
                    text = "开通会员享更多权益",
                    color = Color.White,
                    fontSize = H5,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                )
                Text(
                    text = "了解详情",
                    color = Color.White,
                    fontSize = H6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterEnd)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Color.Black, Color.White)))
                        .padding(horizontal = 18.dp, vertical = 8.dp)

                )

            }

            SettingCommon(
                drawableInt = R.mipmap.ic_bells, title = "消息中心",
                modifier = Modifier.clickable {
                    //点击事件
                })

            SettingCommon(
                drawableInt = R.mipmap.ic_rich_list, title = "富豪榜",
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.mipmap.ic_weather, title = "天气预报",
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.drawable.ic_subscribe_author, title = "小默文章",
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.mipmap.ic_article, title = "我的文章",
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.mipmap.ic_creation_center, title = "创作中心",
                imageColor = Color(0xFFF5B725),
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.drawable.ic_bookmark, title = "我的收藏",
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.drawable.ic_paint, title = "高清壁纸",
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.mipmap.ic_qq_logo, title = "QQ交流群",
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.mipmap.ic_feedback, title = "意见反馈",
                imageColor = Color(0xFF409EFF),
                modifier = Modifier.clickable {
                    //点击事件
                })
            SettingCommon(
                drawableInt = R.mipmap.ic_setting, title = "设置",
                imageColor = Color(0xFF409EFF),
                modifier = Modifier.clickable {
                    //点击事件
                })
        }

    }
}