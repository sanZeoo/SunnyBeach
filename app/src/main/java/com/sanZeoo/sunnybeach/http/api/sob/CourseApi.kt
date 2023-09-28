package com.sanZeoo.sunnybeach.http.api.sob

import com.sanZeoo.sunnybeach.http.ServiceCreator
import com.sanZeoo.sunnybeach.http.annotation.SobBaseUrl
import com.sanZeoo.sunnybeach.http.model.HttpData
import com.sanZeoo.sunnybeach.http.model.ListWrapper
import com.sanZeoo.sunnybeach.model.course.Course
import retrofit2.http.GET
import retrofit2.http.Path

@SobBaseUrl
interface CourseApi {

    /**
     * 获取课程列表
     */
    @GET("ct/edu/course/list/{page}")
    suspend fun getCourseList(@Path("page") page: Int): HttpData<ListWrapper<Course>>


    companion object : CourseApi by ServiceCreator.create()
}