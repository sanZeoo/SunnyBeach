package com.sanZeoo.sunnybeach.common.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.sanZeoo.sunnybeach.MyApp
import com.sanZeoo.sunnybeach.execption.ServiceException
import com.sanZeoo.sunnybeach.http.model.HttpData
import com.sanZeoo.sunnybeach.http.model.HttpResult
import com.sanZeoo.sunnybeach.http.model.ListWrapper
import com.sanZeoo.sunnybeach.utils.NetCheckUtil
import com.sanZeoo.sunnybeach.utils.showToast
import kotlinx.coroutines.flow.Flow
import timber.log.Timber


fun <T : Any> ViewModel.simplePager(
    config: AppPagingConfig = AppPagingConfig(),
    callAction: suspend (page: Int) -> HttpData<ListWrapper<T>>
): Flow<PagingData<T>> {
    return pager(config, initialKey = 1) {
        val page = it.key ?: 1
        val response = try {
            HttpResult.Success(callAction.invoke(page))
        } catch (e: Exception) {
            if (NetCheckUtil.checkNet(MyApp.CONTEXT).not()) {
                showToast("没有网络,请重试")
            } else {
                showToast("请求失败，请重试")
            }
            HttpResult.Error(e)
        }
        when (response) {
            is HttpResult.Success -> {
                Timber.d("列表数据 获取数据")
                val data = response.result.data ?: return@pager PagingSource.LoadResult.Error(ServiceException())
                val currentPage = data.currentPage
                val prevKey = if (data.hasPre) currentPage - 1 else null
                val nextKey = if (data.hasNext) currentPage + 1 else null
                return@pager PagingSource.LoadResult.Page(
                    data = response.result.data.list,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            is HttpResult.Error -> {
                PagingSource.LoadResult.Error(response.exception)
            }
        }
    }
}


fun <T : Any> ViewModel.simpleNetWork(
    config: AppPagingConfig = AppPagingConfig(),
    callAction: suspend () -> HttpData<T>
): Flow<PagingData<T>> {
    return pager(config, initialKey = null) {
        val response = try {
            HttpResult.Success(callAction.invoke())
        } catch (e: Exception) {
            if (NetCheckUtil.checkNet(MyApp.CONTEXT).not()) {
                showToast("没有网络,请重试")
            } else {
                showToast("请求失败，请重试")
            }
            HttpResult.Error(e)
        }
        when (response) {
            is HttpResult.Success -> {
                Timber.d("列表数据 获取数据")
                val data = response.result.data ?: return@pager PagingSource.LoadResult.Error(ServiceException())
                val prevKey =  null
                val nextKey =  null
                return@pager PagingSource.LoadResult.Page(
                    data = listOf(data),
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            is HttpResult.Error -> {
                PagingSource.LoadResult.Error(response.exception)
            }
        }
    }
}


fun <K : Any, V : Any> ViewModel.pager(
    config: AppPagingConfig = AppPagingConfig(),
    initialKey: K? = null,
    loadData: suspend (PagingSource.LoadParams<K>) -> PagingSource.LoadResult<K, V>
): Flow<PagingData<V>> {
    val baseConfig = PagingConfig(
        config.pageSize,
        initialLoadSize = config.initialLoadSize,
        prefetchDistance = config.prefetchDistance,
        maxSize = config.maxSize,
        enablePlaceholders = config.enablePlaceholders
    )
    return Pager(
        config = baseConfig,
        initialKey = initialKey
    ) {
        object : PagingSource<K, V>() {
            override suspend fun load(params: LoadParams<K>): LoadResult<K, V> {
                return loadData.invoke(params)
            }

            override fun getRefreshKey(state: PagingState<K, V>): K? {
                return initialKey
            }


        }
    }.flow.cachedIn(viewModelScope)
}