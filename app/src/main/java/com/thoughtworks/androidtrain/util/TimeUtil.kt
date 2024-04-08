package com.thoughtworks.androidtrain.util

fun String.safeToMill(): Long? {
    return try {
        this.toLong()
    } catch (e: Exception) {
        return null
    }
}