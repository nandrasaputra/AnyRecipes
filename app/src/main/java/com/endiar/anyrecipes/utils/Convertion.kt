package com.endiar.anyrecipes.utils

import android.content.Context
import android.util.DisplayMetrics
import com.endiar.anyrecipes.model.FridgeIngredientItemState

fun dpToPx(context: Context, dp: Int) : Int {
    return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun convertMapToIngredients(map: MutableMap<Int, FridgeIngredientItemState>): String {
    val result = StringBuilder()
    map.forEach { entry ->
        if (entry.value.isChecked) {
            result.append(entry.value.menuValue + ", ")
        }
    }
    if (result.length > 2) {
        result.delete((result.length-2),(result.length-1))
    }
    return result.toString()
}