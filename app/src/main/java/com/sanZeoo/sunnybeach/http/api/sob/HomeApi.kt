package com.sanZeoo.sunnybeach.http.api.sob

import com.sanZeoo.sunnybeach.http.ServiceCreator
import com.sanZeoo.sunnybeach.http.annotation.SobBaseUrl
import com.sanZeoo.sunnybeach.http.model.HttpData
import com.sanZeoo.sunnybeach.http.model.ListWrapper
import com.sanZeoo.sunnybeach.model.article.ArticleInfo
import com.sanZeoo.sunnybeach.model.qa.QaInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


@SobBaseUrl
interface HomeApi {


    /**
     * 根据分类id获取内容
     */
    @GET("ct/content/home/recommend/{categoryId}/{page}")
    suspend fun getArticleListByCategoryId(
        @Path("categoryId") categoryId: String,
        @Path("page") page: Int
    ): HttpData<ListWrapper<ArticleInfo>>

    /**
     * 获取推荐内容
     */
    @GET("ct/content/home/recommend/{page}")
    suspend fun getRecommendContent(@Path("page") page: Int): HttpData<ListWrapper<ArticleInfo>>


    /**
     * 获取问答列表
     * page：页码，从 1 开始
     * state：状态，lastest：最新的，noanswer：等待解决的，hot：热门的
     * category：-2（固定参数）
     */
    @GET("ct/wenda/list")
    suspend fun getQaList(
        @Query("page") page: Int,
        @Query("state") state: String,
        @Query("category") category: Int = -2
    ): HttpData<ListWrapper<QaInfo>>

    companion object : HomeApi by ServiceCreator.create()

}