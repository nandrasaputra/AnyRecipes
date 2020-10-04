package com.endiar.anyrecipes.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecoration(
    private val horizontalSpace: Int,
    private val verticalSpace: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val horizontalSpaceInPx = dpToPx(parent.context, horizontalSpace)
        val verticalSpaceInPx = dpToPx(parent.context, verticalSpace)
        if (position == 0) {
            outRect.top = verticalSpaceInPx
        }
        outRect.apply {
            left = horizontalSpaceInPx
            right = horizontalSpaceInPx
            bottom = verticalSpaceInPx
        }
    }
}