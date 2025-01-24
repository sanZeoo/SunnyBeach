package com.sanZeoo.sunnybeach.composereplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sanZeoo.sunnybeach.R


@Composable
fun EditTextMore(){

    var text by rememberSaveable { mutableStateOf("") }

    // 输入框
    val textState = remember{ mutableStateOf(TextFieldValue()) }

    BasicTextField(
        value = textState.value,
        onValueChange = { textState.value = it  },
//            value = text,
//            onValueChange = { text = it },
        //隐藏当前文字
//            textStyle = TextStyle(color = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
    ) { innerTextField ->

        Box {
            Surface {
                Column {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp)
                                .fillMaxWidth()
                                .background(Color(0xFFF7F7F7))
                                .padding(5.dp)
                                .clip(RectangleShape)
                        ) {
                            if (textState.value.text.isEmpty()) Text(
                                text = "写下神评...",
                                color = Color(0x88000000),
                            )
//                                Text(text = text)

//                                Text(
//                                    text = textState.value.annotatedString, inlineContent = mapOf(
//                                        ID_IMAGE to InlineTextContent(
//                                            Placeholder(
//                                                width = LocalTextStyle.current.fontSize,
//                                                height = LocalTextStyle.current.fontSize,
//                                                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
//                                            ),
//                                        ) { target ->
//                                            Image(
//                                                painter = painterResource(id = target.toInt()),
//                                                contentDescription = null
//                                            )
//                                        },
//                                    )
//                                )
//                                AndroidView(
//                                    factory = { context -> TextView(context) },
//                                    update = {
//                                        it.apply {
//                                            textView = this
//                                            setTextColor(Color.Black.toArgb())
//                                            inputType = TYPE_CLASS_TEXT or TYPE_TEXT_FLAG_MULTI_LINE
//                                            maxLines = 8
//                                            isVerticalScrollBarEnabled = true
//                                            background = null
//                                            it.setDefaultEmojiParser()
//
//                                            setText(textState.value.text)
//                                        }
//                                    })
                            innerTextField()

                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_emoji_normal),
                            contentDescription = "表情",
                            modifier = Modifier.size(30.dp)
                        )

                        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                            Text(
                                text = "0",
                                color = Color(0xffCBD0D3),
                                modifier = Modifier.padding(5.dp)
                            )

                            Text(
                                text = "发送", color = Color.White, modifier = Modifier
                                    .padding(start = 5.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xffBBBBBB))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)

                            )
                        }
                    }
                }
            }
        }
    } // 输入框
}