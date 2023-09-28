package com.sanZeoo.sunnybeach.utils.emoji

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import androidx.core.content.ContextCompat
import com.sanZeoo.sunnybeach.MyApp

class EmojiImageGetter(private val textSize: Int) : Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable? {
        val uri = Uri.parse(source)
        val authority = uri.authority
        // Timber.d("onBindViewHolder：===> source is $source authority is $authority")
        val drawable = if (authority == "cdn.sunofbeaches.com") {
            val fileName = uri.lastPathSegment.toString().replace(".png", "")
            val emojiId = fileName.toIntOrNull() ?: return null
            // 如果图片表情不在范围内，则直接跳过解析
            if (emojiId < 1 || emojiId > 130) {
                return null
            }
//             Timber.d("onBindViewHolder：===> fileName is $EMOJI_PREFIX$fileName")
            val drawable = (EMOJI_PREFIX + fileName).loadLocalDrawableOrNull()
            drawable?.setBounds(10, 0, textSize + 10, textSize)
            drawable
        } else {
            null
        }
        return drawable
    }

    private fun String?.loadLocalDrawableOrNull(): Drawable? {
        if (this == null) return null
        return try {
            val resId = MyApp.CONTEXT.resources.getIdentifier(this,"mipmap",MyApp.CONTEXT.packageName)

            ContextCompat.getDrawable(MyApp.CONTEXT,resId)
//            ResourceUtils.getDrawable(resId)
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
            null
        }
    }

    companion object {

        private const val EMOJI_PREFIX = "emoji_"
    }
}