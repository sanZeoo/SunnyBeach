package com.sanZeoo.sunnybeach.ui.page.course

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.sanZeoo.sunnybeach.common.picture.Banner
import com.sanZeoo.sunnybeach.ktx.isZero
import com.sanZeoo.sunnybeach.theme.CourseColor
import com.sanZeoo.sunnybeach.theme.CourseFreeColor
import com.sanZeoo.sunnybeach.theme.CourseTextColor
import com.sanZeoo.sunnybeach.theme.GreyItemBg
import com.sanZeoo.sunnybeach.theme.H6
import com.sanZeoo.sunnybeach.ui.widget.AppToolsBar
import com.sanZeoo.sunnybeach.ui.widget.RefreshGridView
import com.sanZeoo.sunnybeach.viewmodel.course.CourseViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CoursePage(
    navController: NavHostController,
    viewModel: CourseViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewStates.collectAsState()
    val courseList = viewState.value.courseList?.collectAsLazyPagingItems()
    val isRefreshing = viewState.value.commonState.isLoading
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        courseList?.refresh()
    })
    val pagerState = rememberPagerState(5,0f) { 10 }
    Column {
        AppToolsBar(title = "课程")
        RefreshGridView(lazyPagingItems = courseList,
            pullRefreshState = pullRefreshState,
            headView = {
                Banner(state = pagerState, picData = { courseList?.get(it)!!.cover })
            }) {
            items(courseList!!.itemCount) { index ->
                val course = courseList[index]!!
                val price = if (course.price.isZero) "免费" else "¥ ${course.price}"
                val textColor = if (course.price.isZero) CourseFreeColor else CourseColor
                Card(
                    colors = CardDefaults.cardColors(Color.Transparent),
                    modifier = Modifier
                        .border(4.dp, GreyItemBg)
                        .padding(start = 4.dp, bottom = 4.dp)
                ) {
                    AsyncImage(
                        model = course.cover,
                        contentDescription = "封面",
                        modifier = Modifier
                            .height(120.dp)
                    )
                    Text(
                        text = course.title,
                        maxLines = 1,
                        fontSize = H6,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    Row(Modifier.padding(vertical = 10.dp, horizontal = 4.dp)) {
                        AsyncImage(
                            model = course.avatar,
                            contentDescription = "头像",
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(40.dp))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = course.teacherName,fontSize = H6)
                            Text(
                                text = price,
                                textAlign = TextAlign.Center,
                                color = textColor,
                                fontSize = H6,
                                modifier = Modifier
                                    .clip(RectangleShape)
                                    .background(CourseTextColor)
                                    .width(99.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

