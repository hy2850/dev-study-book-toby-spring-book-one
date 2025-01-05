package com.hcpark.springbook.learningtest.templatecallback

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class CalcSumTest {

    @Test
    fun sumOfNumbers() {
        val calculator = Calculator()

        val sum = calculator.calcSum(this::class.java.classLoader.getResource("numbers.txt")?.path)

        assertEquals(10, sum)
    }
}