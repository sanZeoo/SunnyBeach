package com.sanZeoo.sunnybeach.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sanZeoo.sunnybeach.theme.H5
import com.sanZeoo.sunnybeach.theme.ToolBarHeight
import com.sanZeoo.sunnybeach.theme.ToolBarTitleSize
import com.sanZeoo.sunnybeach.ui.page.common.BottomNavRoute

/**
 * 底部 bottom bar
 */
@Composable
fun BottomNavBarView(navController: NavHostController) {
    val bottomNavList = listOf(
        BottomNavRoute.Fish,
        BottomNavRoute.Qa,
        BottomNavRoute.Article,
        BottomNavRoute.Course,
        BottomNavRoute.My
    )
    // 导航栏栈
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 目的地
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        containerColor = Color.Transparent
    ) {
        bottomNavList.forEach { replyDestination ->
            val isSelected = currentDestination?.route == replyDestination.routeName
            BottomNavigationItem(
                selected = isSelected,
                icon = {
                    val icon = if (!isSelected) {
                        painterResource(id = replyDestination.unselectedIcon)
                    } else {
                        painterResource(id = replyDestination.selectedIcon)
                    }
                    Icon(
                        painter = icon,
                        contentDescription = stringResource(id = replyDestination.stringId),
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(replyDestination.stringId),
                        fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground
                    )
                },
                onClick = {
                    if (currentDestination?.route != replyDestination.routeName) {
                        navController.navigate(replyDestination.routeName) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun AppToolsBgBar(
    title: String,
    rightText: String? = null,
    onBack: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
    imageVector: ImageVector? = null,
    painter: Painter? = null,
) {
//    #00ffff
    val linear = Brush.linearGradient(listOf(Color(0xff0099ff), Color(0xff00ccff)))
    AppToolsBar(
        title = title,
        rightText,
        onBack,
        onRightClick,
        imageVector,
        painter,
        modifier = Modifier.background(linear)
    )
}

/**
 * 普通标题栏头部
 */
@Composable
fun AppToolsBar(
    title: String,
    rightText: String? = null,
    onBack: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
    imageVector: ImageVector? = null,
    painter: Painter? = null,
    modifier: Modifier? = null
) {
    val modifierBg: Modifier = modifier
        ?: Modifier
            .background(Color.Transparent)
    Box(
        modifier = modifierBg
            .fillMaxWidth()
            .padding(top = 30.dp)
            .height(ToolBarHeight)

    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (onBack != null) {
                androidx.compose.material.Icon(
                    Icons.Default.ArrowBack,
                    null,
                    Modifier
                        .clickable(onClick = onBack)
                        .align(Alignment.CenterVertically)
                        .padding(12.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (!rightText.isNullOrEmpty() && imageVector == null) {
                TextContent(
                    text = rightText,
                    color = MaterialTheme.colorScheme.onPrimary,//.mainColor,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 20.dp)
                        .clickable { onRightClick?.invoke() }
                )
            }

            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 12.dp)
                        .clickable {
                            onRightClick?.invoke()
                        })
            }
            if (painter != null) {
                Icon(
                    painter = painter,
                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 12.dp)
                        .clickable {
                            onRightClick?.invoke()
                        })
            }
        }
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontSize = if (title.length > 14) H5 else ToolBarTitleSize,
            fontWeight = FontWeight.W500,
            maxLines = 1
        )

    }
}
