package com.shpt.core.ext

import java.text.SimpleDateFormat

fun String.toDate(format: String) = SimpleDateFormat(format).parse(this)
