package com.hcpark.springbook.learningtest.reflection

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class ReflectionLearningTest {

    @Test
    fun StringReflectionTest() {
        val str = "hello"

        val lengthMethod = String::class.java.getMethod("length")
        val length = lengthMethod.invoke(str)
        assertEquals(str.length, length)

        val charAtMethod = String::class.java.getMethod("charAt", Int::class.javaPrimitiveType)
        val char = charAtMethod.invoke(str, 0) as Char
        assertEquals(str[0], char)
    }
}