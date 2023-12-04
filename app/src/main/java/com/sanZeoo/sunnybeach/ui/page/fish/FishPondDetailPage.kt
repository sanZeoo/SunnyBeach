package com.sanZeoo.sunnybeach.ui.page.fish

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.buildSpannedString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.common.fish.FishMoment
import com.sanZeoo.sunnybeach.common.user.UserDetailView
import com.sanZeoo.sunnybeach.ktx.setDefaultEmojiParser
import com.sanZeoo.sunnybeach.model.fish.FishPondComment
import com.sanZeoo.sunnybeach.model.fish.SubComment
import com.sanZeoo.sunnybeach.theme.CommentBgColor
import com.sanZeoo.sunnybeach.theme.CommentColor
import com.sanZeoo.sunnybeach.theme.GreyItemBg
import com.sanZeoo.sunnybeach.ui.widget.AppToolsBgBar
import com.sanZeoo.sunnybeach.ui.widget.RefreshListView
import com.sanZeoo.sunnybeach.viewmodel.fish.FishPondViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FishPondDetailPage(
    navController: NavHostController,
    momentId: String?,
    fishPondViewModel: FishPondViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        if (momentId != null) {
            fishPondViewModel.getFishDetailData(momentId)
        }
    }

    val viewState = fishPondViewModel.viewStates.collectAsState()
    val fishDetail = viewState.value.fishDetail?.collectAsLazyPagingItems()
    val commentList = viewState.value.fishCommentList?.collectAsLazyPagingItems()

    val isRefreshing = viewState.value.commonState.isLoading

    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        if (momentId != null) {
            fishPondViewModel.getFishDetailData(momentId)
        }
    })

    var text by rememberSaveable { mutableStateOf("") }

//    Timber.d("id ===>%s", momentId)
    Column(modifier = Modifier.fillMaxSize()) {

        AppToolsBgBar(title = "摸鱼详情",
            onBack = { navController.navigateUp() },
            imageVector = Icons.Default.MoreVert,
            contentColor = Color.White,
            onRightClick = {})

        RefreshListView(
            lazyPagingItems = commentList,
            pullRefreshState = pullRefreshState,
            headView = {
                if (fishDetail != null && fishDetail.itemCount > 0) {
                    fishDetail[0]?.let { FishMoment(fishItem = it, isExpanded = true) }
                }
                Divider()
            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.92f)
        ) {
            items(commentList!!.itemCount) { index ->
                val fishPondComment = commentList[index]!!
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    UserDetailView(
                        avatar = fishPondComment.avatar,
                        title = fishPondComment.nickname,
                        leftSubtitle = fishPondComment.position,
                        rightSubtitle = fishPondComment.createTime,
                        fontWeight = FontWeight.Normal,
                        titleTextSize = 18
                    )
                    Icon(
                        painter = painterResource(id = R.mipmap.ic_pond_comment),
                        contentDescription = "评论",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )

                    Column(modifier = Modifier.padding(top = 50.dp, start = 50.dp)) {

                        AndroidView(
                            factory = { context -> TextView(context) },
                            update = {

                                it.setDefaultEmojiParser()
                                it.text = fishPondComment.content
                            }
                        )

                        Column(
                            modifier = Modifier
                                .background(color = GreyItemBg)
                                .padding(4.dp)
                        ) {
                            val subComments = fishPondComment.subComments
                            subComments.getOrNull(0)?.let { subComment ->
                                AndroidView(
                                    factory = { context -> TextView(context) },
                                    update = {
                                        it.setDefaultEmojiParser()
                                        it.text = getBeautifiedFormat(subComment, fishPondComment)
                                    }
                                )
                            }

                            subComments.getOrNull(1)?.let { subComment ->
                                AndroidView(
                                    factory = { context -> TextView(context) },
                                    update = {
                                        it.setDefaultEmojiParser()
                                        it.text = getBeautifiedFormat(subComment, fishPondComment)
                                    }
                                )
                            }
                            if (subComments.size > 2) {
                                Text(text = "查看全部${subComments.size}条回复")
                            }
                        }

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = GreyItemBg)
                )

            }
        }

        Divider()

        Box(modifier = Modifier
            .padding(10.dp)
            .height(50.dp)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(CommentBgColor)) {
            Text(text = "写下神评...",
                color = CommentColor,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 15.dp)
                )
        }


    }

}

fun getBeautifiedFormat(
    subComment: SubComment,
    item: FishPondComment
): Spanned {
    // 谁回复的
    val whoReplied =
        subComment.nickname + if (subComment.id == item.id) "(作者)" else ""
    // 被回复的人
    val wasReplied = subComment.targetUserNickname
    val content = whoReplied + "回复" + wasReplied + "：" + subComment.content
    val startIndex = whoReplied.length + 2
    val color = Color(0xFF045FB2).toArgb()
    return buildSpannedString {
        append(content)
        setSpan(
            ForegroundColorSpan(color),
            content.indexOf(whoReplied),
            content.indexOf("回复"),
            SpannableString.SPAN_INCLUSIVE_INCLUSIVE
        )
        setSpan(
            ForegroundColorSpan(color),
            startIndex,
            startIndex + wasReplied.length,
            SpannableString.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}
