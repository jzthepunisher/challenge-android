package com.bcp.androidchallenge.presentation.ui.exchangerate.list.adapter

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bcp.androidchallenge.R

class DividerCategoryTop(context: Context) : RecyclerView.ItemDecoration(){

    private val divider = ContextCompat.getDrawable(context, R.drawable.divider_item_adapter)!!
    private val margin = context.resources.getDimension(R.dimen.margin_divider_expense).toInt()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for ((index) in parent.children.withIndex()) {
            val view = parent.getChildAt(index)

            if (index != (parent.childCount - 1)) {
                val params = view.layoutParams as RecyclerView.LayoutParams
                val top = view.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }
}