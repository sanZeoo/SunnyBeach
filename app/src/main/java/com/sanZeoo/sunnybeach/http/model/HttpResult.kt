package com.sanZeoo.sunnybeach.http.model

import java.lang.Exception

// ? extend 泛型  用来网络操作--
sealed class HttpResult<out T> {

    data class Success<T>(val result: T):HttpResult<T>()
    data class Error(val exception: Exception):HttpResult<Nothing>()
}