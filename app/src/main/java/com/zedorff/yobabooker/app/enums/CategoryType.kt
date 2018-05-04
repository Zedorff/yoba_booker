package com.zedorff.yobabooker.app.enums

enum class CategoryType constructor(val value: Int){
    REGULAR(0),
    TRANSFER_OUT(1),
    TRANSFER_IN(2);

    companion object {
        fun from(value: Int) = CategoryType.values().first { it.value == value }
    }
}