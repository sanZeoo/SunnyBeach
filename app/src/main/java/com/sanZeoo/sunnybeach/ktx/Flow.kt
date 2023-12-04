package com.sanZeoo.sunnybeach.ktx

import com.sanZeoo.sunnybeach.execption.NotLoginException
import com.sanZeoo.sunnybeach.execption.ServiceException
import com.sanZeoo.sunnybeach.http.Repository
import com.sanZeoo.sunnybeach.http.model.HttpData
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <reified T> launchNetAndHandle(
    crossinline action: suspend () -> HttpData<T>,
    crossinline data : T.() ->Unit ={},
    crossinline exception: RuntimeException.()->Unit = {},
)= flow {
        emit(action.invoke())
    }.map {
        if (it.success) {
            it.data?.data()
        } else when (it.code) {
            Repository.NOT_LOGIN_CODE -> {
                NotLoginException(it.message).exception()
            }
            else -> {
                ServiceException(it.message).exception()
            }
        }
    }
