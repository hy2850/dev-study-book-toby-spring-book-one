package com.hcpark.springbook.user.domain

data class User(
    val id: String = "",
    val name: String = "",
    val password: String = "",
    val level: Level = Level.BASIC,
    val loginCnt: Int = 0,
    val recommendCnt: Int = 0,
) {
    constructor(level: Level) : this(id = "", name = "", password = "", level = level, loginCnt = 0, recommendCnt = 0)

    fun upgradeLevel(): User {
        val nextLevel = level.next ?: throw IllegalStateException("${level}은 업그레이드가 불가능합니다.")
        return copy(level = nextLevel) // shallow copy
    }
}