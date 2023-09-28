package com.sanZeoo.sunnybeach.ktx

import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.unicodeToString(): String {
    var str = this
    val pattern: Pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))")
    val matcher: Matcher = pattern.matcher(str)
    var ch: Char
    runCatching {
        while (matcher.find()) {
            //group 6728
            val group: String? = matcher.group(2)
            group ?: return this
            //ch:'木' 26408
            ch = group.toInt(16).toChar()
            //group1 \u6728
            val group1: String? = matcher.group(1)
            group1 ?: return this
            str = str.replace(group1, ch.toString() + "")
        }
    }
    return str
}

fun String?.ifNullOrEmpty(defaultValue: () -> String) = if (isNullOrEmpty()) defaultValue() else this