package com.hcpark.springbook.user.domain

enum class Level(val value: Int) {
    BASIC(1),
    SILVER(2),
    GOLD(3);

    companion object {
        fun valueOf(value: Int): Level {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("No level with value $value found")
        }
    }
}