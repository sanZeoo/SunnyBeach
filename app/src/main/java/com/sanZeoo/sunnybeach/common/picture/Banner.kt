package com.sanZeoo.sunnybeach.common.picture

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(
    pageCount:Int =10,  //多少数据
    timeMillis: Long =3000, //播放多少秒下一章图
    repeatCount : Int =5,  // 底部有多少个圈圈
    state : PagerState = rememberPagerState(),
    picData : (Int)->String ,
) {
    val scope = rememberCoroutineScope()
    //协程 监听state.settledPage 滚动截止瞬间
    LaunchedEffect(state.settledPage){
        delay(timeMillis)
        // 可适配超过5张图的（+1>5 返回0 ）
        val scroller =if (state.currentPage+1==pageCount) 0 else state.currentPage+1
        state.animateScrollToPage(scroller)
    }
    //轮播图
    Box {
        HorizontalPager(
            pageCount = pageCount,
            state = state,
            modifier = Modifier.fillMaxWidth(),
        ) {
            //缩放动画
            val imgScale by animateFloatAsState(
                targetValue = if (state.currentPage == it) 1f else 0.8f,
                animationSpec = tween(300),
                label = ""
            )
            AsyncImage(
                model = picData.invoke(it),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(imgScale)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.Center) {
            repeat(repeatCount){
                // 适配超过5个 当前位置取余5 颜色才变
                val color = if (state.currentPage%repeatCount == it) Color(0xFF424242) else Color(0xffcbcdcf)
                Box(
                    modifier = Modifier
                        .padding(end = 7.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(7.dp)
                        .clickable {
                            scope.launch {
                                // 可以适配超过5个的 ,在左边 -（当前位置-要位移位置.）右边+（当前位置-要位移位置）
                                state.animateScrollToPage(it)
                            }
                        }
                )
            }
        }
    }
}