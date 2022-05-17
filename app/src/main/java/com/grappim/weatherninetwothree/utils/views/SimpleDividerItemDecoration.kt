package com.grappim.weatherninetwothree.utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.utils.extensions.drawable
import com.grappim.weatherninetwothree.utils.extensions.toPx

class SimpleDividerItemDecoration(context: Context) : ItemDecoration() {

    private val mDivider: Drawable? = context.drawable(R.drawable.line_divider)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + 16.toPx.toInt()
        val right = parent.width - parent.paddingRight - 16.toPx.toInt()
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom = top + (mDivider?.intrinsicHeight ?: 0)
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }

}