package com.zedorff.dragandswiperecycler.helper

interface SDHelperListener {
    fun dragDropEnabled(): Boolean
    fun swipeEnabled(): Boolean
    fun onDragged(from: Int, to: Int)
    fun onDragDropEnded()
    fun onSwiped(position: Int)
}