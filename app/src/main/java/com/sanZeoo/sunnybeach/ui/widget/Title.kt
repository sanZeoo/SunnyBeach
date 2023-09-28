package com.sanZeoo.sunnybeach.ui.widget

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.sanZeoo.sunnybeach.theme.H6


@Composable
fun TextContent(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSecondary,
    maxLines: Int = 99,
    textAlign: TextAlign = TextAlign.Start,
    canCopy: Boolean = false,
    isLoading: Boolean = false
) {
    if (canCopy) {
        SelectionContainer {
            Title(
                title = text,
                modifier = modifier,
                fontSize = H6,
                color = color,
                maxLine = maxLines,
                textAlign = textAlign,
                isLoading = isLoading
            )
        }
    } else {
        Title(
            title = text,
            modifier = modifier,
            fontSize = H6,
            color = color,
            maxLine = maxLines,
            textAlign = textAlign,
            isLoading = isLoading
        )
    }

}

@Composable
fun Title(
    title: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    color: Color = MaterialTheme.colorScheme.onSecondary,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLine: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    isLoading: Boolean = false
) {
    Text(
        text = title,
        modifier = modifier,
        fontSize = fontSize,
        color = color,
        maxLines = maxLine,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign
    )
}