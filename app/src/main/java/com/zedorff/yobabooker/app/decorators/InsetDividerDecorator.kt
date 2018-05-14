package com.zedorff.yobabooker.app.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

public class InsetDividerDecorator(context: Context, private var leftInset: Int = 0, private var rightInset: Int = 0) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null
    private var orientation: Int = 0

    init {
        val a = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividers(canvas, parent)
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDividers(canvas, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }

        orientation = (parent.layoutManager as LinearLayoutManager).orientation
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = divider!!.intrinsicWidth
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = divider!!.intrinsicHeight
        }
    }

    private fun drawHorizontalDividers(canvas: Canvas, parent: RecyclerView) {
        val parentTop = parent.paddingTop
        val parentBottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val parentLeft = leftInset + child.right + params.rightMargin
            val parentRight = rightInset + parentLeft + divider!!.intrinsicWidth

            divider!!.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            divider!!.draw(canvas)
        }
    }

    private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {
        val parentLeft = leftInset + parent.paddingLeft
        val parentRight = rightInset + parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val parentTop = child.bottom + params.bottomMargin
            val parentBottom = parentTop + divider!!.intrinsicHeight

            divider!!.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            divider!!.draw(canvas)
        }
    }
}