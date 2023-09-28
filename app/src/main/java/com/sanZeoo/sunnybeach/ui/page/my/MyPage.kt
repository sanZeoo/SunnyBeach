package com.sanZeoo.sunnybeach.ui.page.my

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.sanZeoo.sunnybeach.ui.widget.AppToolsBgBar

@Composable
fun MyPage(navController: NavHostController) {
    Column {
        AppToolsBgBar(title = "我")
    }
}