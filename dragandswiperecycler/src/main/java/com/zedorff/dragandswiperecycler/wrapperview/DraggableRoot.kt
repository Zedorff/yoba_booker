package com.zedorff.dragandswiperecycler.wrapperview

import android.content.Context
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.zedorff.dragandswiperecycler.extensions.dip
import kotlinx.android.synthetic.main.draggable_root_layout.view.*

class DraggableRoot(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {

    init {
        setOnClickListener {}
    }

    fun enableDragHandler() {
        drag_handler.visibility = View.VISIBLE
    }

    fun setElevationDip(elevation: Int) {
        ViewCompat.setElevation(this, dip(elevation).toFloat())
    }

    fun setDragHandlerTouchListener(listener: View.OnTouchListener?) {
        drag_handler.setOnTouchListener(listener)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        onTouchEvent(ev)
        return super.onInterceptTouchEvent(ev)
    }
}