package com.endiar.anyrecipes.utils

fun tabTitleProvider(position: Int) : String {
    return when(position) {
        0 -> "Overview"
        1 -> "Steps"
        2 -> "Nutrition"
        else -> throw Exception("Invalid Position")
    }
}