package com.sanZeoo.sunnybeach.utils.emoji

import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ImageSpan
import com.sanZeoo.sunnybeach.MyApp
import com.sanZeoo.sunnybeach.utils.emoji.EmojiMapHelper.getEmojiValue
import java.util.regex.Pattern

class EmojiInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {
        val sourceText = source.toString()  // 原文本
        // 新输入的字符串为空（删除剪切等）
        if (TextUtils.isEmpty(source)) {
            return ""
        }
//        val emojiMap = EmojiMapHelper.emojiMap
        // Span 富文本格式
        val ssb = SpannableStringBuilder(source)

//        val currentTimeMillis = System.currentTimeMillis(
        replaceEmojiTextByImgBetter(sourceText,ssb)
        // 效率慢 当有成千上万表情包时 当有 以 [ 开头时才调用富文本,当以 ] 结尾调用map.get方法 为空不替换，
//        for ((emoji) in emojiMap) {
//            replaceEmojiTextByImg(emoji, sourceText, ssb)
//        }
//        Timber.d("耗时 ${currentTimeMillis-System.currentTimeMillis()}")
        return ssb
    }


    private fun replaceEmojiTextByImgBetter(
        sourceText: String?,
        ssb: SpannableStringBuilder){
        sourceText ?: return
        var startIndex = -1
        var lastIndex = -1
        for (i in sourceText.indices) {
            //开始坐标
            if (sourceText[i] == '['){
                startIndex = i
            }
            if (sourceText[i] == ']'){
                // 没有前坐标 ,前一个前坐标被匹配
                if (startIndex == -1 || lastIndex > startIndex) continue
                lastIndex = i
                val emoji = sourceText.substring(startIndex, lastIndex+1)
                // 匹配
                val resId=getEmojiValue(emoji)
                if (resId==0) continue
                val imageSpan = ImageSpan(MyApp.CONTEXT, resId)
                lastIndex = startIndex + emoji.length
                ssb.setSpan(imageSpan, startIndex, lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

    }

    private fun replaceEmojiTextByImg(
        emoji: String?,
        sourceText: String?,
        ssb: SpannableStringBuilder
    ) {
        emoji ?: return
        sourceText ?: return
        val pattern = Pattern.compile(emoji)
        val matcher = pattern.matcher(sourceText)
        var lastIndex = 0
        while (matcher.find()) {
            //
            val startIndex = sourceText.indexOf(emoji, lastIndex)
            if (startIndex == -1) return
            val resId = getEmojiValue(emoji)
            val imageSpan = ImageSpan(MyApp.CONTEXT, resId)
            lastIndex = startIndex + emoji.length
            ssb.setSpan(imageSpan, startIndex, lastIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            // Timber.d("filter：===> startIndex is %s lastIndex is %s", startIndex, lastIndex);
        }
    }
}