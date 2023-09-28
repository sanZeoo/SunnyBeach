package com.sanZeoo.sunnybeach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat

import com.sanZeoo.sunnybeach.theme.SunnyBeachTheme
import com.sanZeoo.sunnybeach.ui.page.common.AppScaffold
import com.sanZeoo.sunnybeach.ui.page.splash.SplashPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false) // 状态栏隐藏（可占用）

        setContent {
            var isSplashScreenShow by remember { mutableStateOf(true) }

            SunnyBeachTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isSplashScreenShow) {
                        SplashPage{isSplashScreenShow = false}
                    } else {
                        AppScaffold()
                    }
                }
            }
        }
    }
}



