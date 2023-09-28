package com.sanZeoo.sunnybeach.model.article


data class ArticleInfo(
    val avatar: String,
    val covers: List<String>,
    val createTime: String,
    val id: String,
    val isVip: Boolean,
    val nickName: String,
    val thumbUp: Int,
    val title: String,
    val type: Int,
    val userId: String,
    val viewCount: Int
)