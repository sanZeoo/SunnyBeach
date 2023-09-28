package com.sanZeoo.sunnybeach.http.api.sob

import com.sanZeoo.sunnybeach.http.ServiceCreator
import com.sanZeoo.sunnybeach.http.annotation.SobBaseUrl
import com.sanZeoo.sunnybeach.http.model.HttpData
import com.sanZeoo.sunnybeach.http.model.ListWrapper
import com.sanZeoo.sunnybeach.model.fish.FishItem
import com.sanZeoo.sunnybeach.model.fish.FishPondComment
import com.sanZeoo.sunnybeach.model.fish.FishPondTopicList
import retrofit2.http.GET
import retrofit2.http.Path


@SobBaseUrl
interface FishPondApi {

    /**
     * 获取话题列表
     */
    @GET("ct/moyu/topic")
    suspend fun loadTopicList(): HttpData<FishPondTopicList>


    /**
     * 获取动态列表
     */
    @GET("ct/moyu/list/{topicId}/{page}")
    suspend fun loadFishListById(
        @Path("topicId") topicId: String,
        @Path("page") page: Int
    ): HttpData<ListWrapper<FishItem>>

//    /**
//     * 获取动态详情
//     */
//    @GET("ct/moyu/{momentId}")
//    suspend fun loadFishDetailById(@Path("momentId") momentId: String): HttpData<Fish.FishItem>

    /**
     * 获取动态评论
     */
    @GET("ct/moyu/comment/{momentId}/{page}?sort=1")
    suspend fun getFishCommendListById(
        @Path("momentId") momentId: String,
        @Path("page") page: Int
    ): HttpData<FishPondComment>

    companion object : FishPondApi by ServiceCreator.create()
}