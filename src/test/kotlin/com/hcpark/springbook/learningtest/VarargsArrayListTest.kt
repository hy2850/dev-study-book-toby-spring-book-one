package com.hcpark.springbook.learningtest

import kotlin.test.Test

class VarargsArrayListTest {

    fun printVarargs(vararg names: String) {
        names.forEach { println(it) }
    }

    fun printArray(names: Array<String>) {
        names.forEach { println(it) }
    }

    fun printList(names: List<Any>) {
        names.forEach { println(it) }
    }

    @Test
    fun printTest() {
        printVarargs("a", "b", "c")
        printArray(arrayOf("a", "b", "c"))
        printList(listOf("a", "b", "c"))
    }

    @Test
    fun varArgsIsArray() {
        val names = arrayOf("a", "b", "c")
        printVarargs(*names) // spread operator - https://stackoverflow.com/questions/39389003/kotlin-asterisk-operator-before-variable-name-or-spread-operator-in-kotlin
        printArray(names)
    }
}