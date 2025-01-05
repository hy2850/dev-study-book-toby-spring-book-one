package com.hcpark.springbook.user.domain

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class LevelTest {

    @Test
    fun builtIn_valueOfTest() {
        // given
        val basic = Level.BASIC

        // when
        val level = Level.valueOf("BASIC")

        // then
        assertEquals(basic, level)
    }

    @Test
    fun custom_valueOfTest() {
        // given
        val basic = Level.BASIC

        // when
        val level = Level.valueOf(1)

        // then
        assertEquals(basic, level)
    }
}