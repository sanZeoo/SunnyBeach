package com.sanZeoo.sunnybeach.viewmodel.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sanZeoo.sunnybeach.common.CommonState
import com.sanZeoo.sunnybeach.common.paging.simplePager
import com.sanZeoo.sunnybeach.http.api.sob.CourseApi
import com.sanZeoo.sunnybeach.model.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class CourseViewState(
    val courseList : QaItem?,
    val commonState: CommonState = CommonState()
)

@HiltViewModel
class CourseViewModel @Inject constructor() :ViewModel() {
    private var _viewStates = MutableStateFlow(CourseViewState(courseList = loadCourseList()))
    var viewStates = _viewStates.asStateFlow()

    private fun loadCourseList(): Flow<PagingData<Course>> {
        return simplePager {
            CourseApi.getCourseList(it)
        }.cachedIn(viewModelScope)
    }

}

typealias QaItem = Flow<PagingData<Course>>