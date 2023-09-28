package com.sanZeoo.sunnybeach.http

import com.sanZeoo.sunnybeach.config.BASE_URL
import com.sanZeoo.sunnybeach.http.interceptor.BaseUrlInterceptor
import com.sanZeoo.sunnybeach.http.interceptor.loggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * author : A Lonely Cat
 * github : https://github.com/anjiemo/SunnyBeach
 * time   : 2021/10/02
 * desc   : 网络请求服务创建者
 */
object ServiceCreator {

    val retrofit: Retrofit by lazy { createRetrofit { baseUrl(BASE_URL) } }

//    private val cookieManager = LocalCookieManager.get()

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(BaseUrlInterceptor { retrofit })
//            .addInterceptor(accountInterceptor)
            .addInterceptor(loggingInterceptor)
//            .cookieJar(cookieManager)
            .build()
    }

    private fun createRetrofit(block: Retrofit.Builder.() -> Retrofit.Builder) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .run(block)
        .build()

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}