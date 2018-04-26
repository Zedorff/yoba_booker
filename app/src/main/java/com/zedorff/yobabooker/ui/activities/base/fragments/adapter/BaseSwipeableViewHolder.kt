package com.zedorff.yobabooker.ui.activities.base.fragments.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.inflate
import kotlinx.android.synthetic.main.swipable_background.view.*

open class BaseSwipeableViewHolder<T: ViewDataBinding>(parent: ViewGroup, var binding: T,
        var root: ViewGroup = parent.inflate(R.layout.swipable_root_layout) as ViewGroup) : RecyclerView.ViewHolder(root) {

    var background: View = root.inflate(R.layout.swipable_background)
    var delete: View = background.delete_icon_root
    var foreground: View = binding.root

    init {
        root.addView(background, 0)
        root.addView(foreground, 1)
    }
}