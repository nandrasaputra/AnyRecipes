package com.endiar.anyrecipes.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class FridgeIngredientDecoration(
    private val horizontalSpace: Int = 0,
    private val verticalSpace: Int = 0,
    private val positionShift: Int = 0
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val spanSize = (parent.layoutManager as GridLayoutManager).spanSizeLookup.getSpanSize(position)
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val column = (position - positionShift) % spanCount
        val horizontalSpaceInPx = dpToPx(view.context, horizontalSpace)
        val verticalSpaceInPx = dpToPx(view.context, verticalSpace)

        if (spanCount != spanSize) {
            if (positionShift > 0) {
                if (position !in 0 until positionShift) {
                    outRect.apply {
                        left = horizontalSpaceInPx * (spanCount - column) / spanCount
                        right = horizontalSpaceInPx * (column + 1) / spanCount
                        bottom = verticalSpaceInPx
                        if ((position - positionShift) < spanCount) {
                            top = verticalSpaceInPx
                        }
                    }
                }
            } else {
                outRect.apply {
                    left = horizontalSpaceInPx * (spanCount - column) / spanCount
                    right = horizontalSpaceInPx * (column + 1) / spanCount
                    bottom = verticalSpaceInPx
                    if ((position - positionShift) < spanCount) {
                        top = verticalSpaceInPx
                    }
                }
            }
        }



    }
}