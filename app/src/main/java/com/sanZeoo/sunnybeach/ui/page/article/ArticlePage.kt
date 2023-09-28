package com.sanZeoo.sunnybeach.ui.page.article

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.common.BottomBehaviorView
import com.sanZeoo.sunnybeach.common.picture.NineGridView
import com.sanZeoo.sunnybeach.common.user.UserDetailView
import com.sanZeoo.sunnybeach.theme.GreyItemBg
import com.sanZeoo.sunnybeach.ui.widget.RefreshListView
import com.sanZeoo.sunnybeach.utils.showToast
import com.sanZeoo.sunnybeach.viewmodel.article.ArticleViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ArticlePage(
    navController: NavHostController,
    viewModel: ArticleViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewStates.collectAsState()
    val articleList = viewState.value.articleList?.collectAsLazyPagingItems()
    val isRefreshing = viewState.value.commonState.isLoading
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        articleList?.refresh()
    })
    Column {
        SearchBar(
            modifier = Modifier.clickable {
                //跳转页面
                showToast("点击搜索")
            },
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text("搜索") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            enabled = false
        ) {}
        RefreshListView(lazyPagingItems = articleList, pullRefreshState = pullRefreshState) {
            items(articleList!!.itemCount) { index ->
                val articleInfo = articleList[index]!!
                UserDetailView(
                    avatar = articleInfo.avatar,
                    title = articleInfo.title,
                    leftSubtitle = articleInfo.nickName,
                    rightSubtitle = articleInfo.createTime,
                    titleTextSize = 18
                )
                if (articleInfo.covers.isNotEmpty()) {
                    NineGridView(articleInfo.covers)
                }
                BottomBehaviorView(
                    commentPic = R.mipmap.ic_view,
                    commentCount = articleInfo.viewCount,
                    thumbUpCount = articleInfo.thumbUp
                )
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