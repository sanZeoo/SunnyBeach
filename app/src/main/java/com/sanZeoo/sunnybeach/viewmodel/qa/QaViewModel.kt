package com.sanZeoo.sunnybeach.viewmodel.qa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sanZeoo.sunnybeach.common.CommonState
import com.sanZeoo.sunnybeach.common.paging.simplePager
import com.sanZeoo.sunnybeach.config.QaType
import com.sanZeoo.sunnybeach.http.api.sob.HomeApi
import com.sanZeoo.sunnybeach.model.qa.QaInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class QaViewState(
    val qaList : QaItem?,
    val commonState: CommonState = CommonState()
)


@HiltViewModel
class QaViewModel @Inject constructor():ViewModel() {

    private var _viewStates = MutableStateFlow(QaViewState(qaList = loadQaList(QaType.LATEST)))
    var viewStates = _viewStates.asStateFlow()

    private fun loadQaList(qaType: QaType): Flow<PagingData<QaInfo>> {
        return simplePager {
            HomeApi.getQaList(it,qaType.value)
        }.cachedIn(viewModelScope)
    }


}


typealias QaItem = Flow<PagingData<QaInfo>>

