package com.zedorff.yobabooker.app.enums

enum class TransactionType constructor(val value: Int) {
    OUTCOME(0),
    INCOME(1),
    TRANSFER(2),
    EMPTY(3);

    companion object {
        fun from(value: Int) = TransactionType.values().first { it.value == value }
    }
}