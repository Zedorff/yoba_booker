package com.zedorff.yobabooker.app.extensions

inline fun <T> Iterable<T>.sumBy(selector: (T) -> Float): Float {
    var sum: Float = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Float.toNegative(): Float = this * -1