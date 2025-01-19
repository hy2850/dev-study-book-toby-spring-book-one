package com.hcpark.springbook.user.domain

data class User(
    val id: String = "",
    var name: String = "",
    var password: String = "",
    var level: Level = Level.BASIC,
    var loginCnt: Int = 0,
    var recommendCnt: Int = 0,
) {
    fun upgradeLevel() {
        val nextLevel = level.next ?: throw IllegalStateException("${level}은 업그레이드가 불가능합니다.")
        level = nextLevel
    }
}