package com.thoughtworks.androidtrain.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.safeToMill(): Long? {
    return try {
        this.toLong()
    } catch (e: Exception) {
        return null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

@RequiresApi(Build.VERSION_CODES.O)
fun Long.formatDateString(): String {
    return formatter.format(
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this),
            ZoneId.systemDefault()
        )
    )
}