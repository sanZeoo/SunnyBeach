package com.sanZeoo.sunnybeach.model.fish

data class FishPondComment(
    val avatar: String,
    val company: String,
    val content: String,
    val createTime: String,
    val id: String,
    val momentId: String,
    val nickname: String,
    val position: String,
    val subComments: List<SubComment>,
    val thumbUp: Int,
    val thumbUpList: List<Any>,
    val userId: String,
    val vip: Boolean
)

data class SubComment(
    val avatar: String,
    val commentId: String,
    val company: String,
    val content: String,
    val createTime: String,
    val id: String,
    val nickname: String,
    val position: String,
    val targetUserId: String,
    val targetUserIsVip: Boolean,
    val targetUserNickname: String,
    val thumbUpList: List<Any>,
    val userId: String,
    val vip: Boolean
)