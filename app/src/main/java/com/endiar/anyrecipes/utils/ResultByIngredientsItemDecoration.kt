package com.endiar.anyrecipes.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 *  ItemDecoration For GridLayoutManager With 2 Span Size, Designed For Working in ResultByIngredientsFragment
 *
 */
class ResultByIngredientsItemDecoration(
    private val horizontalSpace: Int = 0,
    private val verticalSpace: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % 2
        val horizontalSpaceInPx = dpToPx(view.context, horizontalSpace)
        val verticalSpaceInPx = dpToPx(view.context, verticalSpace)

        if (column == 0) {
            if (position == 0) {
                outRect.top = verticalSpaceInPx
            }
            outRect.apply {
                outRect.apply {
                    left = horizontalSpaceInPx
                    right = horizontalSpaceInPx / 2
                    bottom = verticalSpaceInPx
                }
            }
        } else {
            if (position == 1) {
                outRect.top = verticalSpaceInPx
            }
            outRect.apply {
                right = horizontalSpaceInPx
                left = horizontalSpaceInPx / 2
                bottom = verticalSpaceInPx
            }
        }

    }
}