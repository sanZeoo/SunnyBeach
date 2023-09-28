package com.sanZeoo.sunnybeach.model.qa


//
//data class Data(
//    val currentPage: Int,
//    val hasNext: Boolean,
//    val hasPre: Boolean,
//    val list: List<QaInfo>,
//    val pageSize: Int,
//    val total: Int,
//    val totalPage: Int
//)

data class QaInfo(
    val answerCount: Int,
    val avatar: String,
    val categoryId: String,
    val categoryName: String,
    val createTime: String,
    val id: String,
    val isResolve: String,
    val isVip: String,
    val label: Any,
    val labels: List<String>,
    val nickname: String,
    val sob: Int,
    val state: Any,
    val thumbUp: Int,
    val title: String,
    val userId: String,
    val viewCount: Int
)