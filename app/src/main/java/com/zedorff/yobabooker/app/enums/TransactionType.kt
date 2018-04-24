package com.zedorff.yobabooker.app.enums

enum class TransactionType constructor(val value: Int) {
    INCOME(0),
    OUTCOME(1),
    TRANSFER(2),
    EMPTY(3);

    companion object {
        fun from(value: Int) = TransactionType.values().first { it.value == value }
    }
}