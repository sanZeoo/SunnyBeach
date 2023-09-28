package com.sanZeoo.sunnybeach.model.fish


class FishPondTopicList : ArrayList<FishPondTopicList.FishPondTopicListItem>(){
    data class FishPondTopicListItem(
        val contentCount: Int,
        val cover: String,
        val description: String,
        val followCount: Int,
        val hasFollowed: Boolean,
        val id: String,
        val topicName: String
    )
}

