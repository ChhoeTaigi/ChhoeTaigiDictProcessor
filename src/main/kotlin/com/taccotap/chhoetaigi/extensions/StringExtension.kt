package com.taccotap.chhoetaigi.extensions

fun String.isNumeric(): Boolean = this.matches("-?\\d+(\\.\\d+)?".toRegex())
