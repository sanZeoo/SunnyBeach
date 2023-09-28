package com.sanZeoo.sunnybeach.ui.page.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sanZeoo.sunnybeach.R

sealed class BottomNavRoute(
    var routeName: String,
    @StringRes var stringId: Int,
    @DrawableRes var selectedIcon: Int,
    @DrawableRes var unselectedIcon: Int
) {
    object Fish : BottomNavRoute(RouteName.FISH, R.string.fish, R.drawable.home_fish_pond_on_ic,R.drawable.home_fish_pond_off_ic)
    object Qa : BottomNavRoute(RouteName.QA, R.string.qa, R.drawable.home_found_on_ic,R.drawable.home_found_off_ic)
    object Article : BottomNavRoute(RouteName.ARTICLE, R.string.article, R.drawable.home_home_on_ic,R.drawable.home_home_off_ic)
    object Course : BottomNavRoute(RouteName.COURSE, R.string.course, R.drawable.home_course_on_ic,R.drawable.home_course_off_ic)
    object My : BottomNavRoute(RouteName.MY, R.string.my, R.drawable.home_me_on_ic,R.drawable.home_me_off_ic)
}