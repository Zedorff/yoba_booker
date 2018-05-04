package com.zedorff.dragandswiperecycler.viewholder

enum class SDState constructor(val value: Int){
    IDLE(0),
    SWIPING(1),
    DRAGGING(2);

    companion object {
        fun from(value: Int) = SDState.values().first { it.value == value }
    }
}