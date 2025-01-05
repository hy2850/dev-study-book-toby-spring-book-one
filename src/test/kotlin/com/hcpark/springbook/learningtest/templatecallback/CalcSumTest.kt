package com.hcpark.springbook.learningtest.templatecallback

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import kotlin.test.Test

class CalcSumTest {

    @Test
    fun sumOfNumbers() {
        val sum = calculator.calcSum(numFilePath)
        assertEquals(10, sum)
    }

    @Test
    fun mulOfNumbers() {
        val sum = calculator.calcMultiply(numFilePath)
        assertEquals(24, sum)
    }

    @Test
    fun concatOfNumbers() {
        val sum = calculator.calcConcat(numFilePath)
        assertEquals("1 2 3 4 ", sum)
    }

    companion object {
        private lateinit var calculator: Calculator
        private var numFilePath: String? = null

        @JvmStatic
        @BeforeAll
        fun setUp() {
            calculator = Calculator()
            numFilePath = this::class.java.classLoader.getResource("numbers.txt")?.path
        }
    }
}