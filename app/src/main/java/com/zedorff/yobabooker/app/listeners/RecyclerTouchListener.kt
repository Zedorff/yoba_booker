package com.zedorff.yobabooker.app.listeners

interface RecyclerTouchListener {
    fun dragDropEnabled(): Boolean
    fun swipeEnabled(): Boolean
    fun onDragged(from: Int, to: Int)
    fun onDragDropEnded()
    fun onSwiped(position: Int)
}