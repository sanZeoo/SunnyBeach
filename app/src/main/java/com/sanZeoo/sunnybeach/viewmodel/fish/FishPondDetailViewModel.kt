package com.sanZeoo.sunnybeach.viewmodel.fish

import androidx.lifecycle.ViewModel
import com.sanZeoo.sunnybeach.common.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class FishPondDetailState(
//    val fishPondDetailList : QaItem?,
    val commonState: CommonState = CommonState()
)

@HiltViewModel
class FishPondDetailViewModel @Inject constructor():ViewModel() {

}