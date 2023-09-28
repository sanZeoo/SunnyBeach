package com.sanZeoo.sunnybeach.ktx


import java.lang.Exception
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale


fun String.getFriendlyTimeSpanByNow(dateFormat: DateFormat):String{
    return this.string2Millis(dateFormat).getFriendlyTimeSpanByNow()
}

fun String.string2Millis(dateFormat: DateFormat) :Long {
    try {
        return dateFormat.parse(this)?.time ?:-1
    }catch (e : Exception){
        e.stackTrace
    }
    return -1
}

fun Long.getFriendlyTimeSpanByNow() :String{
    val defaultValue = this
    val now  = System.currentTimeMillis()
    val span = now - defaultValue
    if (span < 0)
    // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
        return String.format("%tc", defaultValue);
    if (span < 1000) {
        return "刚刚";
    } else if (span < TimeConstants.MIN) {
        return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC)
    } else if (span < TimeConstants.HOUR) {
        return String.format(Locale.getDefault(), "%d分钟前", span / TimeConstants.MIN)
    }
    // 获取当天 00:00

    val wee = getWeeOfToday()
    return if (defaultValue >= wee) {
        String.format("今天%tR", defaultValue);
    } else if (defaultValue >= wee - TimeConstants.DAY) {
        String.format("昨天%tR", defaultValue);
    } else {
        String.format("%tF", defaultValue);
    }
}

object TimeConstants{
    const val MSEC = 1
    const val SEC  = 1000
    const val MIN  = 60000
    const val HOUR = 3600000
    const val DAY  = 86400000


}

fun getWeeOfToday() : Long{
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis;
}

