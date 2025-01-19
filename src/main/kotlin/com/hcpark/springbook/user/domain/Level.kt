package com.hcpark.springbook.user.domain

enum class Level(val value: Int, val next: Level?) {
    GOLD(3, GOLD),
    SILVER(2, GOLD),
    BASIC(1, SILVER);

    companion object {
        fun valueOf(value: Int): Level {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("No level with value $value found")
        }
    }
}