package com.hcpark.springbook.user.domain

data class User(
    val id: String = "",
    var name: String = "",
    var password: String = "",
    var level: Level = Level.BASIC,
    var loginCnt: Int = 0,
    var recommendCnt: Int = 0,
) {
   fun nextLevel(): Level {
        return when (level) {
            Level.BASIC -> Level.SILVER
            Level.SILVER -> Level.GOLD
            Level.GOLD -> Level.GOLD
        }
   }
}