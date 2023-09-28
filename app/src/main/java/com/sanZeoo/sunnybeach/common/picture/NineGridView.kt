package com.sanZeoo.sunnybeach.common.picture

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage


@Composable
fun NineGridView(
    images: List<String>
) {
    val imageCount = images.size
    var fixed = 1
    var fixedHeight = 1
    if (imageCount > 1) {
        fixed = if (imageCount % 2 == 0) 2 else 3
        fixedHeight =
            imageCount / fixed + if (imageCount % fixed == 0) 0 else 1
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(fixed), userScrollEnabled = false,
        modifier = androidx.compose.ui.Modifier.height(fixedHeight * 120.dp)
    ) {
        items(imageCount) { index ->
            AsyncImage(
                model = images[index], contentDescription = "图片",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
        }
    }
}