package com.sanZeoo.sunnybeach.http.model


data class HttpData<T>(
    val code: Int,
    val data: T?,
    val message: String,
    val success: Boolean
)


data class ListWrapper<T>(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPre: Boolean,
    val list: List<T>,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int
)