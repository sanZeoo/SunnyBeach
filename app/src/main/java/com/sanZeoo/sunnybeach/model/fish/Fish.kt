package com.sanZeoo.sunnybeach.model.fish

data class Fish(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class Data(
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPre: Boolean,
    val list: List<FishItem>,
    val pageSize: Int,
    val total: Int,
    val totalPage: Int
)

data class FishItem(
    val avatar: String,
    val commentCount: Int,
    val company: String,
    val content: String,
    val createTime: String,
    val hasThumbUp: Boolean,
    val id: String,
    val images: List<String>,
    val linkCover: String,
    val linkTitle: String,
    val linkUrl: String,
    val nickname: String = "",
    val position: String,
    val thumbUpCount: Int,
    val thumbUpList: List<String>,
    val topicId: String,
    val topicName: String,
    val userId: String,
    val vip: Boolean
)
