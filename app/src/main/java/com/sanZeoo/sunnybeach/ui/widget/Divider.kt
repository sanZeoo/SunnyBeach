package com.sanZeoo.sunnybeach.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListDivider(horizontal: Int = 1,alpha: Float = 0.08f) {
    Divider(
        modifier = Modifier.padding(horizontal = horizontal.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = alpha)
    )
}