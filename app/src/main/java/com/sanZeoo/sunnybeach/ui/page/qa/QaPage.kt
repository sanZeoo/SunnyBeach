package com.sanZeoo.sunnybeach.ui.page.qa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.ktx.getFriendlyTimeSpanByNow
import com.sanZeoo.sunnybeach.theme.AnswerGreenColor
import com.sanZeoo.sunnybeach.theme.DefaultFontColor
import com.sanZeoo.sunnybeach.theme.GreyItemBg
import com.sanZeoo.sunnybeach.theme.H6
import com.sanZeoo.sunnybeach.theme.H7
import com.sanZeoo.sunnybeach.theme.WindowBgColor
import com.sanZeoo.sunnybeach.common.user.UserView
import com.sanZeoo.sunnybeach.ui.widget.AppToolsBar
import com.sanZeoo.sunnybeach.ui.widget.ListDivider
import com.sanZeoo.sunnybeach.ui.widget.RefreshListView
import com.sanZeoo.sunnybeach.viewmodel.qa.QaViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QaPage(
    navController: NavHostController,
    viewModel: QaViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewStates.collectAsState()
    val qaList = viewState.value.qaList?.collectAsLazyPagingItems()
    val isRefreshing = viewState.value.commonState.isLoading
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        qaList?.refresh()
    })

    Column {
        AppToolsBar(title = "问答")
        ListDivider()

        RefreshListView(lazyPagingItems = qaList, pullRefreshState = pullRefreshState) {
            items(qaList!!.itemCount) { index ->
                val qaInfo = qaList[index]!!
                val isResolve = qaInfo.isResolve.toIntOrNull() == 1
                val answerCount = qaInfo.answerCount
                val hasAnswer = answerCount > 0
                val answerColor = AnswerGreenColor  //绿色
                val mSdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 6.dp)
                ) {
                    Text(
                        text = qaInfo.title,
                        color = Color.Black,
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        fontSize = H6,
                        letterSpacing = 0.1.sp
                    )
                    UserView(avatar = qaInfo.avatar, name = qaInfo.nickname, imageSize = 20)
                    LazyRow {
                        val labels = qaInfo.labels
                        items(labels.size) { index ->
                            Text(
                                text = labels[index],
                                fontSize = H7,
                                color = DefaultFontColor,
                                modifier = Modifier.background(WindowBgColor)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val answerTextColor = when {
                            isResolve -> Color.White
                            hasAnswer -> answerColor
                            else -> Color.Unspecified
                        }
                        val answerBgColor = if (isResolve) answerColor else Color.Transparent
                        val answerBorColor = if (hasAnswer) answerColor else Color.White
                        Text(
                            text = "${if (isResolve) "√" else ""} ${qaInfo.answerCount} 答案",
                            color = answerTextColor,
                            fontSize = H6,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(80.dp)
                                .background(answerBgColor)
                                .border(1.dp, answerBorColor, shape = CircleShape)
                                .padding(horizontal = 4.dp)

                        )

                        Image(
                            painter = painterResource(id = R.mipmap.ic_view),
                            contentDescription = "",
                            modifier = Modifier
                                .size(width = 50.dp, height = 28.dp)
                                .padding(start = 20.dp)
                        )
                        Text(text = "${qaInfo.viewCount}", fontSize = H6)

                        Image(
                            painter = painterResource(id = R.mipmap.ic_gold_currency_1),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .size(width = 20.dp, height = 38.dp)
                        )
                        Text(text = "${qaInfo.sob}", fontSize = H6)

                        Text(
                            text = qaInfo.createTime.getFriendlyTimeSpanByNow(mSdf),
                            fontSize = H6,
                            modifier = Modifier.padding(start = 50.dp)
                        )

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(color = GreyItemBg)
                )
            }
        }

    }
}