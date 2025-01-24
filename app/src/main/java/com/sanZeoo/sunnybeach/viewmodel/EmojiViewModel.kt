package com.sanZeoo.sunnybeach.viewmodel

import androidx.lifecycle.ViewModel
import com.sanZeoo.sunnybeach.utils.emoji.EmojiMapHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class EmojiViewState(
    val emojiList: List<String> ,
)

@HiltViewModel
class EmojiViewModel @Inject constructor() :ViewModel() {

    private var _viewStates = MutableStateFlow(EmojiViewState(emojiList = getEmojiList()))
    var viewStates = _viewStates.asStateFlow()


    private val emojiListData by lazy {
        getEmojiList()
    }

    private fun getEmojiList() : List<String> {
        val emojiList = arrayListOf<String>()
        EmojiMapHelper.bilibiliEmojiMap.onEach {
            val emoji = it.key
            emojiList.add(emoji)
        }
        return emojiList
    }
}