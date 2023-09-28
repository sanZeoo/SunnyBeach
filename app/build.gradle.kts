@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
//    alias()
}

android {
    namespace = "com.sanZeoo.sunnybeach"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.sanZeoo.sunnybeach"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    kotlin {
        jvmToolchain(8)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    signingConfigs {

        create("config") {
            keyAlias = "key0"
            keyPassword = "AndroidProject"
            storeFile = file("D:\\ComposeTest\\SunnyBeach\\AndroidProject.jks")
            storePassword = "AndroidProject"
        }
    }


    buildTypes {

        debug {
            // 给包名添加后缀
            applicationIdSuffix = ".debug"
            // 调试模式开关
            isDebuggable = true
            isJniDebuggable = true
            // 压缩对齐开关
//            isZipAlignEnabled = true
            // 移除无用的资源
            isShrinkResources = false
            // 代码混淆开关
            isMinifyEnabled = false

            // 签名信息配置
            signingConfig = signingConfigs.getByName("config"){
                // 添加清单占位符
                addManifestPlaceholders(
                    mapOf(Pair("app_name", "阳光沙滩 Debug 版"))
                )
            }

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )


            // 调试模式下只保留一种架构的 so 库，提升打包速度
            ndk {
                abiFilters.add("armeabi-v7a")
            }
        }

        // leakcanary内存泄漏
//        preview.initWith(debug)
//        preview {
//            applicationIdSuffix ''
//            // 添加清单占位符
//            addManifestPlaceholders([
//                'app_name': '阳光沙滩 Preview 版'
//            ])
//        }

        release {
            // 调试模式开关
            isDebuggable = false
            isJniDebuggable = false
            // 压缩对齐开关
            isZipAlignEnabled = true
            // 移除无用的资源
            isShrinkResources = true
            // 代码混淆开关
            isMinifyEnabled = true
            // 签名信息配置
            signingConfig = signingConfigs.getByName("config") {
                // 添加清单占位符
                addManifestPlaceholders(
                    mapOf(Pair("app_name", "@string/app_name"))
                )
            }

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // 仅保留两种架构的 so 库，根据 Bugly 统计得出
            ndk {
                // armeabi：万金油架构平台（占用率：0%）
                // armeabi-v7a：曾经主流的架构平台（占用率：10%）
                // arm64-v8a：目前主流架构平台（占用率：95%）
                abiFilters.add("armeabi-v7a")
                abiFilters.add("arm64-v8a")
            }
        }
    }
}



dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // lottie 动画库
    implementation(libs.lottie.compose)
    // navigation 底部
    implementation(libs.androidx.navigation.navigation.compose)

    implementation("androidx.compose.material:material")
    //http request
    implementation(libs.squareup.converter.gson)
    implementation(libs.squareup.retrofit)
    implementation(libs.com.google.code.gson.gson)
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    implementation("com.github.getActivity:GsonFactory:5.2")

    // 日志打印框架：https://github.com/JakeWharton/timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // hilt 依赖注入框架
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")

    // coil 图片加载
    implementation("io.coil-kt:coil-compose:2.4.0")

    //paging
    implementation("androidx.paging:paging-compose:3.2.0")
//    implementation("androidx.paging:paging-runtime:3.1.1")
    // 单activity 可以不加 不涉及生命周期
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    //下拉刷新 已移植material design3  已被PullRefresh取代 box+pullRefresh
//    implementation ("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")

    //系统ui控制器
//    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.0-alpha")
    // 沉浸式 适配
//    implementation ("com.geyifeng.immersionbar:immersionbar:3.2.2")

}

kapt {
    correctErrorTypes = true
}