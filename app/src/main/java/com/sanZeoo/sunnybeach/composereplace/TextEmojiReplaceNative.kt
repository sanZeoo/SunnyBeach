package com.sanZeoo.sunnybeach.composereplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.text.parseAsHtml
import com.sanZeoo.sunnybeach.utils.emoji.EmojiImageGetter
import com.sanZeoo.sunnybeach.utils.emoji.EmojiMapHelper

const val ID_IMAGE = "image"

@Composable
fun EmojiText(
    htmlText:String ,
    isParseHtml:Boolean,
){
    val text = if (isParseHtml)
        htmlText.parseAsHtml(imageGetter = EmojiImageGetter(16))
    else htmlText

    val textShow = buildAnnotatedString {
        var startIndex = -1  // 左括号的标记
        var lastIndex = -1  // 右括号的标记
        for (i in text.indices) {
            //开始坐标
            if (text[i] == '['){
                startIndex = i
                // 当遇到左括号直接 append
                if (lastIndex+1 < startIndex) {
                    append(text.substring(lastIndex + 1, startIndex))
                }
            }
            if (text[i] == ']'){
                // 没有前坐标 ,前一个前坐标被匹配
                if (startIndex == -1 || lastIndex > startIndex) continue
                lastIndex = i
                val emoji = text.substring(startIndex, lastIndex+1)
                // 匹配  找到 图片本地资源
                val resId= EmojiMapHelper.getEmojiValue(emoji)
                if (resId==0) {
                    append(text.substring(startIndex+1,i))
                    continue
                }else{
                    lastIndex = startIndex + emoji.length
                    appendInlineContent(ID_IMAGE,resId.toString())
                }
            }
        }
        if (lastIndex+1 <= text.length){
            append(text.substring(lastIndex+1))
        }
    }

    Text(text = textShow, inlineContent = mapOf(
        ID_IMAGE to InlineTextContent(
            Placeholder(
                width = LocalTextStyle.current.fontSize,
                height = LocalTextStyle.current.fontSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
            ),
        ) { target ->
            Image(painter = painterResource(id = target.toInt()), contentDescription = null )
        }
    ))

}

fun buildEmojiString(text:String){
    buildAnnotatedString {
        var startIndex = -1  // 左括号的标记
        var lastIndex = -1  // 右括号的标记
        for (i in text.indices) {
            //开始坐标
            if (text[i] == '['){
                startIndex = i
                // 当遇到左括号直接 append
                if (lastIndex+1 < startIndex) {
                    append(text.substring(lastIndex + 1, startIndex))
                }
            }
            if (text[i] == ']'){
                // 没有前坐标 ,前一个前坐标被匹配
                if (startIndex == -1 || lastIndex > startIndex) continue
                lastIndex = i
                val emoji = text.substring(startIndex, lastIndex+1)
                // 匹配  找到 图片本地资源
                val resId= EmojiMapHelper.getEmojiValue(emoji)
                if (resId==0) {
                    append(text.substring(startIndex+1,i))
                    continue
                }else{
                    lastIndex = startIndex + emoji.length
                    appendInlineContent(ID_IMAGE,resId.toString())
                }
            }
        }
        if (lastIndex+1 <= text.length){
            append(text.substring(lastIndex+1))
        }
    }
}