package com.sanZeoo.sunnybeach.viewmodel.article

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sanZeoo.sunnybeach.common.CommonState
import com.sanZeoo.sunnybeach.common.paging.simplePager
import com.sanZeoo.sunnybeach.http.api.sob.HomeApi
import com.sanZeoo.sunnybeach.model.article.ArticleInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ArticleViewState(
    val articleList : QaItem?,
    val commonState: CommonState = CommonState()
)

@HiltViewModel
class ArticleViewModel @Inject constructor() :ViewModel() {

    private var _viewStates = MutableStateFlow(ArticleViewState(articleList = loadArticleList("recommend")))
    var viewStates = _viewStates.asStateFlow()

    private fun loadArticleList(categoryId: String): Flow<PagingData<ArticleInfo>> {
        return simplePager {
            if (categoryId.isEmpty() || categoryId.isDigitsOnly().not()) {
                HomeApi.getRecommendContent(page = it)
            } else {
                HomeApi.getArticleListByCategoryId(categoryId = categoryId, it)
            }
        }.cachedIn(viewModelScope)
    }
}

typealias QaItem = Flow<PagingData<ArticleInfo>>