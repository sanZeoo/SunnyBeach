package com.sanZeoo.sunnybeach.viewmodel.fish


import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sanZeoo.sunnybeach.common.CommonState
import com.sanZeoo.sunnybeach.common.paging.simpleNetWork
import com.sanZeoo.sunnybeach.common.paging.simplePager
import com.sanZeoo.sunnybeach.http.api.sob.FishPondApi
import com.sanZeoo.sunnybeach.ktx.launchNetAndHandle
import com.sanZeoo.sunnybeach.model.fish.FishItem
import com.sanZeoo.sunnybeach.model.fish.FishPondComment
import com.sanZeoo.sunnybeach.model.fish.FishPondTopicList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//网络操作 返回值 --更新ui
data class FishPondViewState(
    val topicList: PagingTopicItem?  = null,
    val fishList: PagingFishItem? = null,
    val fishDetail: PagingFishItem? = null,
    val fishCommentList: PagingCommentItem? =null,
    val listState: LazyListState = LazyListState(),
    val commonState: CommonState = CommonState()
)
//Intent 目的
@HiltViewModel
class FishPondViewModel @Inject constructor() : ViewModel() {

    private val fishPondPager by lazy {
        getFishPond()
    }

    private val topicListPager by lazy {
        getTopList()
    }

    private var _viewStates = MutableStateFlow(FishPondViewState())
    var viewStates = _viewStates.asStateFlow()

    fun getFishPagerData(){
        _viewStates.update { it.copy(fishList = fishPondPager, topicList = topicListPager)}
    }

    fun getFishDetailData(momentId:String){
        _viewStates.update { it.copy(fishDetail = getFishDetail(momentId), fishCommentList = getFishCommentListById(momentId))}
    }

     fun refresh(isNeed:Boolean =false ,onRefresh: (() -> Unit) = {}) {
         viewModelScope.launch {
             _viewStates.update { it.copy(commonState = CommonState(isLoading = true)) }

             if (isNeed) {
                 _viewStates.update {
                     it.copy(topicList = getTopList())
                 }
             }
             onRefresh.invoke()
             _viewStates.update {
                 it.copy(fishList = getFishPond(), commonState = CommonState(isLoading = false))
             }
//            _viewStates.update {  it.copy(commonState = CommonState(isLoading = false))}
         }
     }

    private fun getFishPond() = simplePager {
        FishPondApi.loadFishListById("recommend",it)
    }.cachedIn(viewModelScope)


    private fun getTopList() = simpleNetWork {
        FishPondApi.loadTopicList()
    }.cachedIn(viewModelScope)
    //获取推荐话题
    private fun fetchTopic()  {
        viewModelScope.launch {
            launchNetAndHandle(
                { FishPondApi.loadTopicList() },
//                data = {_viewStates.update {
//                    it.copy(topicList = this)
//                }},
                exception = { _viewStates.update {
                    it.copy(commonState = CommonState(failed = this))
                } }
            )
                .onStart { Timber.d("加载中 ") }
                .catch { Timber.d("异常 --- $it") }
                .collect()

        }
    }


    private fun getFishDetail(momentId :String) = simpleNetWork {
        FishPondApi.loadFishDetailById(momentId)
    }.cachedIn(viewModelScope)

    private fun getFishCommentListById(momentId: String) = simplePager {
        FishPondApi.getFishCommendListById(momentId = momentId,it)
    }

}

typealias PagingTopicItem = Flow<PagingData<FishPondTopicList>>
typealias PagingFishItem = Flow<PagingData<FishItem>>
typealias PagingCommentItem = Flow<PagingData<FishPondComment>>