package com.sanZeoo.sunnybeach.ui.page.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sanZeoo.sunnybeach.R

@Composable
fun SplashPage(onAnimated: () -> Unit) {
//    val anim = rememberLottieAnimatable()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(composition = composition, { progress })

    if (progress == 1.0f) {
        onAnimated.invoke()
    }
    Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp, bottom = 30.dp), contentAlignment = Alignment.BottomCenter){
//        #99000000
        Text(text = "阳光沙滩", color =  Color(0x99000000), fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }


}