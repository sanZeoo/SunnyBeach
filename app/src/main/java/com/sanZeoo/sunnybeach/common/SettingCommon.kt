package com.sanZeoo.sunnybeach.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sanZeoo.sunnybeach.R
import org.w3c.dom.Text

@Composable
fun SettingCommon(
    drawableInt: Int,
    title: String,
    modifier: Modifier = Modifier,
    imageColor :Color? =null
) {
    val colorFilter = if (imageColor!=null) ColorFilter.tint(imageColor) else null
    Row(
        modifier = modifier.padding(top = 16.dp, start = 16.dp, bottom = 6.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = drawableInt),
            contentDescription = "$title 图片",
            colorFilter = colorFilter,
            modifier = Modifier
                .size(42.dp)
                .padding(8.dp)
        )
        Text(text = title, modifier = Modifier.padding(start = 16.dp))
    }
}