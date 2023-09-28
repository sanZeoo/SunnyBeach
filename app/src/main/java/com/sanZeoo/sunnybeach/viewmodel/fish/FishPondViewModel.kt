package com.sanZeoo.sunnybeach.viewmodel.fish


import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sanZeoo.sunnybeach.execption.NotLoginException
import com.sanZeoo.sunnybeach.execption.ServiceException
import com.sanZeoo.sunnybeach.http.Repository
import com.sanZeoo.sunnybeach.model.fish.FishPondTopicList
import com.sanZeoo.sunnybeach.common.CommonState
import com.sanZeoo.sunnybeach.common.paging.simplePager
import com.sanZeoo.sunnybeach.http.api.sob.FishPondApi
import com.sanZeoo.sunnybeach.model.fish.FishItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//网络操作 返回值 --更新ui
data class FishPondViewState(
    val topicList: FishPondTopicList  = FishPondTopicList(),
    val fishList: PagingFishItem?,
    val listState: LazyListState = LazyListState(),
    val commonState: CommonState = CommonState()
)
//Intent 目的
sealed class FishPondViewAction {
    object FetchTopic : FishPondViewAction()
    object FetchFishPond : FishPondViewAction()
    object Refresh  : FishPondViewAction()
}

@HiltViewModel
class FishPondViewModel @Inject constructor() : ViewModel() {

    private val fishPondPager by lazy {
        getFishPond()
    }

//    var viewStates by mutableStateOf(FishPondViewState(fishList = fishPondPager))
//        private set

    private var _viewStates = MutableStateFlow(FishPondViewState(fishList = fishPondPager))
    var viewStates = _viewStates.asStateFlow()

    fun dispatch(action: FishPondViewAction) {
        when (action) {
            is FishPondViewAction.FetchTopic -> fetchTopic()
            is FishPondViewAction.FetchFishPond -> fetchFishPond()
            is FishPondViewAction.Refresh -> refresh()
            else -> {}
        }
    }


     fun refresh( onRefresh: (() -> Unit) = {}){
        viewModelScope.launch {
            _viewStates.update {  it.copy(commonState = CommonState(isLoading = true))}
            if (viewStates.value.topicList.size == 0) {
                fetchTopic()
            }
            onRefresh.invoke()
            _viewStates.update {
                it.copy(fishList = getFishPond(), commonState =CommonState(isLoading = false) )
            }

//            _viewStates.update {  it.copy(commonState = CommonState(isLoading = false))}
        }
    }

    //获取动态列表
    private fun fetchFishPond() {
        getFishPond()
    }

    private fun getFishPond() = simplePager {
        FishPondApi.loadFishListById("recommend",it)
    }.cachedIn(viewModelScope)


    init {
        dispatch(FishPondViewAction.FetchTopic)
//        dispatch(FishPondViewAction.FetchFishPond)
    }

    //获取推荐话题
    private fun fetchTopic() {

        viewModelScope.launch {
            flow { emit(Repository.loadTopicList()) }
                .map {data ->
                    if (data.success) _viewStates.update { it.copy(topicList = data.data!!) }
                    else when (data.code) {
                        Repository.NOT_LOGIN_CODE -> {
                            _viewStates.update {  it.copy(commonState = CommonState(failed = NotLoginException(data.message)))}
                        }

                        else -> _viewStates.update { it.copy(commonState = CommonState(failed = ServiceException(data.message)))}
                    }
                }
                .onStart { Timber.d("加载中 ") }
                .catch { Timber.d("异常 --- $it") }
                .collect()
        }
    }

}

typealias PagingFishItem = Flow<PagingData<FishItem>>