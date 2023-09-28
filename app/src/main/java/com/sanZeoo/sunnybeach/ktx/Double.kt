package com.sanZeoo.sunnybeach.ktx

import java.math.BigDecimal

val Double.isZero: Boolean
    get() = BigDecimal(this).compareTo(BigDecimal.ZERO) == 0