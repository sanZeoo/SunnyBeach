package com.sanZeoo.sunnybeach.ui.page.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.ui.page.article.ArticlePage
import com.sanZeoo.sunnybeach.ui.page.course.CoursePage
import com.sanZeoo.sunnybeach.ui.page.fish.FishPage
import com.sanZeoo.sunnybeach.ui.page.my.MyPage
import com.sanZeoo.sunnybeach.ui.page.qa.QaPage
import com.sanZeoo.sunnybeach.ui.widget.BottomNavBarView
import com.sanZeoo.sunnybeach.utils.showToast


@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    // 导航栏栈
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 目的地
    val currentDestination = navBackStackEntry?.destination

    val rememberCoroutineScope = rememberCoroutineScope()


    Scaffold(
        bottomBar = {
            when(currentDestination?.route){
                RouteName.FISH ->BottomNavBarView(navController)
                RouteName.QA ->BottomNavBarView(navController)
                RouteName.ARTICLE ->BottomNavBarView(navController)
                RouteName.COURSE ->BottomNavBarView(navController)
                RouteName.MY ->BottomNavBarView(navController)
            }
        },
        floatingActionButton = {
            when(currentDestination?.route){
                RouteName.FISH ->  Image(painter = rememberAsyncImagePainter(model = R.mipmap.ic_publish_content),
                    contentDescription = "发动态",
                    modifier = Modifier
                        .size(48.dp)
                        .pointerInput(Unit) { detectTapGestures { showToast("点击按钮") } })
            }
        }
    ) { innerPadding  ->
        //  导航器, 开始导航
        NavHost(navController = navController , modifier = Modifier.padding(innerPadding)
            , startDestination = RouteName.FISH  ){
            composable(route = RouteName.FISH) {
                FishPage(navController,rememberCoroutineScope)
            }
            composable(route = RouteName.QA) {
                QaPage(navController)
            }
            composable(route = RouteName.ARTICLE) {
                ArticlePage(navController)
            }
            composable(route = RouteName.COURSE) {
                CoursePage(navController)
            }
            composable(route = RouteName.MY) {
                MyPage(navController)
            }

        }
    }
}