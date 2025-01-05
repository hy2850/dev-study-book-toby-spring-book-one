package com.hcpark.springbook.user.domain

data class User(
    val id: String = "",
    val name: String = "",
    val password: String = "",
    val level: Level = Level.BASIC,
    val loginCnt: Int = 0,
    val recommendCnt: Int = 0,
)