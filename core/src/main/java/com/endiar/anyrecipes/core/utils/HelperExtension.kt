package com.endiar.anyrecipes.core.utils

fun <T> List<T>?.valueOrEmpty() : List<T> {
    return this ?: listOf()
}