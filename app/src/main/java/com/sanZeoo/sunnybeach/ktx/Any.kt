package com.sanZeoo.sunnybeach.ktx

import com.hjq.gson.factory.GsonFactory

fun Any?.toJson(): String? = GsonFactory.getSingletonGson().toJson(this)