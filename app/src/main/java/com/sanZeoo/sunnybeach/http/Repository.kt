package com.sanZeoo.sunnybeach.http

import com.sanZeoo.sunnybeach.execption.NotLoginException
import com.sanZeoo.sunnybeach.execption.ServiceException
import com.sanZeoo.sunnybeach.http.api.sob.FishPondApi
import com.sanZeoo.sunnybeach.http.model.HttpData
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

object Repository {
    const val NOT_LOGIN_CODE = 11126


    suspend fun loadTopicList() =  FishPondApi.loadTopicList()


    /**
     * 启动并获取数据
     */
    inline fun <reified T> launchAndGetData(
        crossinline action: suspend () -> HttpData<T>
    ) =
        launchAndGet( action = action) { it.data }

    /**
     * 启动并获取消息
     */
    inline fun launchAndGetMsg(crossinline action: suspend () -> HttpData<Any>) =
        launchAndGet( action = action) { it.message }

    /**
     * 启动并获取
     * 返回一个
     */
     inline fun <reified T> launchAndGet(
        // 需要调用的接口
        crossinline action: suspend () -> HttpData<T>,
        // 请求成功时的回调
        crossinline onSuccess: (HttpData<T>) -> Unit
    ) = flow {
         emit(action.invoke())
    }.map {//http返回处理
//        Timber.d("a $it")
        if (it.success) Result.success(onSuccess.invoke(it) )
        else when (it.code) {
            NOT_LOGIN_CODE -> {
//                checkToken()
                Result.failure(NotLoginException(it.message))
            }
            else -> Result.failure(ServiceException(it.message))
        }
    }


}

