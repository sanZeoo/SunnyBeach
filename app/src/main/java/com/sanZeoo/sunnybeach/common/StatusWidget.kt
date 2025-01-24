package com.sanZeoo.sunnybeach.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sanZeoo.sunnybeach.R
import com.sanZeoo.sunnybeach.config.NetWorkStates

@Composable
fun NetWorkStatusWidget(
    states: NetWorkStates,
){

    NetWorkStates.LOADING

}

@Composable
fun StatusWidget(
    text :String? = "网络错误，请重试",
    drawableRes : Int = R.drawable.status_network_ic,
    buttonText : String? ="重试",
    onClick : ()-> Unit,
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(drawableRes))
    val progress by animateLottieCompositionAsState(composition)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier
                .weight(1f)
                .fillMaxWidth())

            LottieAnimation(composition = composition, { progress })
            Spacer(modifier = Modifier.height(10.dp))
            if (text !=null){
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = text, color = Color.Black, fontSize = 14.sp)
            }
            if (buttonText !=null){
                Spacer(modifier = Modifier.height(20.dp))
                Button(shape = RoundedCornerShape(5.dp,5.dp,5.dp,5.dp),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color(0xFF007AFF)
                    ),
                    onClick = {
                    onClick.invoke()
                }) {
                    Text(text = buttonText , color = Color.White, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth())
        }
    }
}