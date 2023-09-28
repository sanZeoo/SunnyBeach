package com.sanZeoo.sunnybeach.common.paging

import androidx.paging.PagingConfig

data class AppPagingConfig(
    val pageSize: Int = 30,
    val initialLoadSize: Int = 30,
    val prefetchDistance:Int = 1,
    val maxSize:Int = PagingConfig.MAX_SIZE_UNBOUNDED,
    val enablePlaceholders:Boolean = false
)