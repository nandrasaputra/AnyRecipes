package com.endiar.anyrecipes.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val space = dpToPx(view.context, 16)
        if (position == 0) {
            outRect.top = space
        }
        outRect.apply {
            left = space
            right = space
            bottom = space
        }
    }
}