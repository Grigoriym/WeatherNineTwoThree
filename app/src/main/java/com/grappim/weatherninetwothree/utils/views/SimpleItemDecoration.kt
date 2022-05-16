package com.grappim.weatherninetwothree.utils.views

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grappim.weatherninetwothree.utils.extensions.dpToPx

class SimpleItemDecoration(
    private val top: Int = 8,
    private val left: Int = 16,
    private val right: Int = 16,
    private val bottom: Int = 8,
    private val context: Context
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = context.dpToPx(top)
        outRect.left = context.dpToPx(left)
        outRect.right = context.dpToPx(right)
        outRect.bottom = context.dpToPx(bottom)
    }
}