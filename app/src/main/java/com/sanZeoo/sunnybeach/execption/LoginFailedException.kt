package com.sanZeoo.sunnybeach.execption

class LoginFailedException(override val message: String = "登录失败") : RuntimeException(message)