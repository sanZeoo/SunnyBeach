package com.sanZeoo.sunnybeach.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun <T : Any> RefreshListView(
    lazyPagingItems: LazyPagingItems<T>?,
    isRefreshing: Boolean = false,
    pullRefreshState: PullRefreshState,
    onRefresh: (() -> Unit) = {},
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier.fillMaxSize(),
    //composable 界面
    headView: @Composable () -> Unit = {},
    stickyHeader: @Composable () -> Unit = {},
    itemContent: LazyListScope.() -> Unit
) {
    val err = lazyPagingItems?.loadState?.refresh is LoadState.Error


    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        // 可以自定义下拉刷新指示器 放在最后 覆盖在最上层
//            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        //动态列表
        LazyColumn(
            state = listState,
            modifier = modifier
        ) {
            stickyHeader {
                stickyHeader.invoke()
            }
            item {
                headView.invoke()
            }
            // 动态列表
            if (lazyPagingItems != null && lazyPagingItems.itemCount > 0) {
                itemContent()
            } else if (err) {
                item {
                    ErrorContent {
                        onRefresh.invoke()
                        lazyPagingItems?.retry()
                    }
                }
            }
        } //LazyColumn  滑动窗口

        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> RefreshGridView(
    lazyPagingItems: LazyPagingItems<T>?,
    isRefreshing: Boolean = false,
    pullRefreshState: PullRefreshState,
    onRefresh: (() -> Unit) = {},
    listState: LazyGridState = rememberLazyGridState(),
    //composable 界面
    headView: @Composable () -> Unit = {},
    itemContent: LazyGridScope.() -> Unit
) {
    val err = lazyPagingItems?.loadState?.refresh is LoadState.Error
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {

        //动态列表
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), state = listState,
            modifier = Modifier.fillMaxSize(),
        ) {
            // 动态列表
            if (lazyPagingItems != null && lazyPagingItems.itemCount > 0) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    headView.invoke()
                }
                itemContent()
            }
        } //LazyColumn  滑动窗口
        if (err) {
            ErrorContent {
                onRefresh.invoke()
                lazyPagingItems?.retry()
            }
        }
        // 可以自定义下拉刷新指示器 放在最后 覆盖在最上层
//        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}


@Composable
fun ErrorContent(
    onRefresh: (() -> Unit) = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "请求出错啦",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            )
            Button(
                onClick = {
                    // 重连
                    onRefresh.invoke()
                    //fishPondViewModel.dispatch(FishPondViewAction.FetchTopic)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
            ) {
                Text(text = "重试")
            }
        }
    }
}