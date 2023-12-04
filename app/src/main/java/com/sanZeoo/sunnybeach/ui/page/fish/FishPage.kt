package com.sanZeoo.sunnybeach.ui.page.fish

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.common.fish.FishMoment
import com.sanZeoo.sunnybeach.theme.GreyItemBg
import com.sanZeoo.sunnybeach.theme.H7
import com.sanZeoo.sunnybeach.ui.page.common.RouteName
import com.sanZeoo.sunnybeach.ui.widget.AppToolsBar
import com.sanZeoo.sunnybeach.ui.widget.ListDivider
import com.sanZeoo.sunnybeach.ui.widget.RefreshListView
import com.sanZeoo.sunnybeach.viewmodel.fish.FishPondViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun FishPage(
    navController: NavHostController,
    rememberCoroutineScope: CoroutineScope,
    fishPondViewModel: FishPondViewModel = hiltViewModel()
) {

    // 加载数据
    LaunchedEffect(Unit){ // Unit=key为null 触发key的时候才会重新加载数据
        fishPondViewModel.getFishPagerData()
    }

    //这里不能耗时操作, 只能是ui刷新相关的数据,不然ui刷新会导致重新加载变量
    val viewState = fishPondViewModel.viewStates.collectAsState()

    val topicList = viewState.value.topicList?.collectAsLazyPagingItems()
    val fishList = viewState.value.fishList?.collectAsLazyPagingItems()
    val listState = if (fishList == null || fishList.itemCount == 0) LazyListState() else viewState.value.listState

    val isRefreshing = viewState.value.commonState.isLoading

    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        fishPondViewModel.refresh {
            fishList?.refresh()
        }
    })

//    val err = fishList?.loadState?.refresh is LoadState.Error

    Column {
        AppToolsBar(title = "鱼塘", painter = painterResource(id = R.drawable.ic_scan_qr_code),
            onRightClick = {
                // 扫码界面
//                showToast("扫描界面")

            })
        ListDivider()


        RefreshListView(lazyPagingItems = fishList, pullRefreshState = pullRefreshState, listState = listState,
            onRefresh = {
                fishPondViewModel.refresh()
            },
            stickyHeader = {
                Text(
                    text = "推荐话题", fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                        .background(Color.White)
                        .padding(8.dp)
                )
            },
            headView = {
                LazyRow {
                    if (topicList!=null && topicList.itemCount>0)
                        topicList[0]?.let {
                            items(it.size){index ->
                                val topicListItem = topicList[0]?.get(index)
                                if (topicListItem != null) {
                                    Column(modifier = Modifier.height(80.dp)) {
                                        AsyncImage(
                                            model = topicListItem.cover,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(60.dp)
                                                .clip(CircleShape)
                                        )

                                        Text(text = topicListItem.topicName, fontSize = H7)

                                    }
                                }
                            }
                        }
                    //推荐话题
                }
            }) {

            items(fishList!!.itemCount) { index ->
                val fishItem = fishList[index]!!

                if (index==0){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(color = GreyItemBg)
                    )
                }
                // 加载框架已经判断了 为空的情况
                FishMoment(fishItem = fishItem, onClick = {
                    navController.navigate(RouteName.FISH_POND_DETAIL + "/${fishItem.id}")
                })

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(color = GreyItemBg)
                )
            }

        }

        //提取公共方法 paging的下拉刷新框架
        //下拉刷新
//        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
//            // 可以自定义下拉刷新指示器 放在最后 覆盖在最上层
////            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
//
//            //动态列表
//            LazyColumn(state = listState, modifier = Modifier.fillMaxSize() ) {
//                // 自定义下拉刷新, 默认移到 第一个 item 动画结束移到 第一个
////                item {
////                    if ((pullRefreshState.progress > 0) || isRefreshing)
////                    Surface(
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .height(40.dp)
////                            .align(Alignment.TopCenter)
////                            .pullRefresh(pullRefreshState),
////                        shape = RoundedCornerShape(10.dp),
////                    ) {
////                        Box {
////                            Text(text = "下拉可以刷新")
////                        }
////                    }
////                }
//
//                stickyHeader {
//                    Text(
//                        text = "推荐话题", fontSize = 14.sp,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(34.dp)
//                            .background(Color.White)
//                            .padding(8.dp)
//                    )
//                }
//                item {
//                    LazyRow {
//                        //推荐话题
//                        items(topicList.size) { index ->
//                            Column(modifier = Modifier.height(80.dp)) {
//                                AsyncImage(
//                                    model = topicList[index].cover,
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(60.dp)
//                                        .clip(CircleShape)
//                                )
//                                Text(text = topicList[index].topicName, fontSize = H7)
//                            }
//                        }
//                    }
//                }
//                Timber.d("当前 数量${fishList?.itemCount}")
//                // 动态列表
//                if (fishList !=null && fishList.itemCount >0 ) {
//                    items(fishList.itemCount) { index ->
//                        val fishItem = fishList[index]!!
//                        val mSdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE)
//
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(6.dp)
//                                .background(color = GreyItemBg)
//                        )
//
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 10.dp, end = 10.dp, bottom = 6.dp)
//                        ) {
//
//                            //用户信息
//                            Row(modifier = Modifier.padding(vertical = 10.dp)) {
//                                AsyncImage(
//                                    model = fishItem.avatar, contentDescription = "头像",
//                                    modifier = Modifier
//                                        .size(40.dp)
//                                        .clip(CircleShape)
//                                )
//                                Column(modifier = Modifier.padding(start = 10.dp)) {
//                                    Text(text = fishItem.nickname, fontSize = 18.sp)
//                                    Text(
//                                        text = "${fishItem.position.ifNullOrEmpty { "摊友" }} · ${
//                                            fishItem.createTime.getFriendlyTimeSpanByNow(mSdf)
//                                        }",
//                                        fontSize = 11.sp,
//                                        color = DefaultFontColor
//                                    )
//                                }
//                            }
////                    val parseAsHtml =
//
//                            // 内容
//                            AndroidView(
//                                factory = { context -> TextView(context) },
//                                update = {
//                                    it.maxLines = 5
//                                    it.ellipsize = TextUtils.TruncateAt.END
//                                    it.setTextColor(Color.Black.toArgb())
//                                    it.setDefaultEmojiParser()
//                                    it.text = fishItem.content.parseAsHtml(
//                                        imageGetter = EmojiImageGetter(it.textSize.toInt())
//                                    )
//                                }
//                            )
//                            Spacer(modifier = Modifier.height(6.dp))
////                    Text(text = parseAsHtml, maxLines = 5,
////                        overflow = TextOverflow.Ellipsis,
////                        textAlign = TextAlign.Start,
////                        style = MaterialTheme.typography.bodyMedium,
////                        letterSpacing = 0.1.sp,
////                        lineHeight = 16.sp
////                    )
//                            val images = fishItem.images
//                            val imageCount = images.size
//                            var fixed = 1
//                            var fixedHeight = 1
//                            if (imageCount > 1) {
//                                fixed = if (imageCount % 2 == 0) 2 else 3
//                                fixedHeight =
//                                    imageCount / fixed + if (imageCount % fixed == 0) 0 else 1
//                            }
//                            // 九宫图
//                            if (imageCount > 0) {
//                                LazyVerticalGrid(
//                                    columns = GridCells.Fixed(fixed), userScrollEnabled = false,
//                                    modifier = Modifier.height(fixedHeight * 120.dp)
//                                ) {
//                                    items(imageCount) { index ->
//                                        AsyncImage(
//                                            model = images[index], contentDescription = "图片",
//                                            contentScale = ContentScale.Crop,
//                                            modifier = Modifier
//                                                .height(120.dp)
//                                                .fillMaxWidth()
//                                        )
//                                    }
//                                }
//                                Spacer(modifier = Modifier.height(6.dp))
//                            }
//                            // 话题标签
//                            val topicName = fishItem.topicName
//                            if (!TextUtils.isEmpty(topicName)) {
//                                Spacer(modifier = Modifier.height(2.dp))
//                                Text(
//                                    text = topicName, fontSize = H7, color = ConfirmTextColor,
//                                    modifier = Modifier
//                                        .border(0.5.dp, ConfirmTextColor, CircleShape)
//                                        .padding(8.dp, 4.dp)
//                                )
//                            }
//
//                            if (!TextUtils.isEmpty(fishItem.linkUrl)) {
//                                Row(modifier = Modifier.background(GreyBg)) {
//                                    AsyncImage(
//                                        model = fishItem.linkCover, contentDescription = "链接",
//                                        placeholder = rememberAsyncImagePainter(model = R.mipmap.ic_link_default),
//                                        error = rememberAsyncImagePainter(model = R.mipmap.ic_link_default),
//                                        modifier = Modifier
//                                            .size(40.dp)
//                                            .clip(CircleShape)
//                                    )
//                                    Spacer(modifier = Modifier.width(10.dp))
//                                    Column {
//                                        Text(
//                                            text = fishItem.linkTitle,
//                                            overflow = TextOverflow.Ellipsis,
//                                            maxLines = 1,
//                                            fontSize = 14.sp
//                                        )
//                                        Text(
//                                            text = fishItem.linkUrl,
//                                            overflow = TextOverflow.Ellipsis,
//                                            color = LinkedTextColor,
//                                            maxLines = 1,
//                                            fontSize = 14.sp
//                                        )
//                                    }
//                                }
//                            }
//                            // 底部栏 一键三连 评论 + 转发 + 点赞
//                            Row(modifier = Modifier.border(0.5.dp, color = Color.White)) {
//
//                                Row(
//                                    modifier = Modifier.weight(1.0f),
//                                    horizontalArrangement = Arrangement.Center,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.share_ic),
//                                        contentDescription = null,
//                                        modifier = Modifier
//                                            .size(40.dp)
//                                            .padding(10.dp),
//                                        colorFilter = ColorFilter.tint(MenuFontColor)
//                                    )
//                                    Text(text = "分享", color = MenuFontColor)
//                                }
//                                val commentCount =
//                                    if (fishItem.commentCount == 0) "评论" else fishItem.commentCount.toString()
//                                Row(
//                                    modifier = Modifier.weight(1.0f),
//                                    horizontalArrangement = Arrangement.Center,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Image(
//                                        painter = rememberAsyncImagePainter(model = R.mipmap.ic_moment_comment),
//                                        contentDescription = null,
//                                        modifier = Modifier
//                                            .size(40.dp)
//                                            .padding(6.dp)
//                                    )
//                                    Text(text = commentCount, color = MenuFontColor)
//                                }
//                                val greatCount =
//                                    if (fishItem.thumbUpCount == 0) "点赞" else fishItem.thumbUpCount.toString()
//                                val fontColor =
//                                    if (fishItem.hasThumbUp) MenuLikeFontColor else MenuFontColor
//                                Row(
//                                    modifier = Modifier.weight(1.0f),
//                                    horizontalArrangement = Arrangement.Center,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Image(
//                                        painter = rememberAsyncImagePainter(model = R.mipmap.ic_great_normal),
//                                        contentDescription = null,
//                                        modifier = Modifier
//                                            .size(40.dp)
//                                            .padding(10.dp),
//                                        colorFilter = ColorFilter.tint(fontColor)
//                                    )
//                                    Text(text = greatCount, color = MenuFontColor)
//                                }
//
//                            }
//
//                        }
//                        //Column
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(6.dp)
//                                .background(color = GreyItemBg)
//                        )
//                    }
//                }else if (err){
//                    item {
//                        Box(modifier = Modifier.fillMaxSize()) {
//                            Column(modifier = Modifier.align(Alignment.Center)) {
//
//                                Text(
//                                    text = "请求出错啦",
//                                    modifier = Modifier
//                                        .align(Alignment.CenterHorizontally)
//                                        .padding(top = 10.dp)
//                                )
//                                Button(
//                                    onClick = {
//                                        fishPondViewModel.dispatch(FishPondViewAction.FetchTopic)
//                                        fishList?.retry() },
//                                    modifier = Modifier
//                                        .align(Alignment.CenterHorizontally)
//                                        .padding(10.dp),
//                                ) {
//                                    Text(text = "重试")
//                                }
//                            }
//                        }
//                    }
//                }
//            } //LazyColumn  滑动窗口
//
//            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
//
//        }

    }
}