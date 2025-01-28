package com.hcpark.springbook.learningtest.proxy

import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test

class HelloProxyTest {

    @Test
    fun HelloTest() {
        val hello = HelloTarget()

        assertTrue(hello.sayHello("Toby") == "Hello Toby")
        assertTrue(hello.sayHi("Toby") == "Hi Toby")
        assertTrue(hello.sayThankYou("Toby") == "Thank you Toby")
    }

    @Test
    fun HelloProxyTest() {
        val hello = HelloUppercase(HelloTarget())

        assertTrue(hello.sayHello("Toby") == "HELLO TOBY")
        assertTrue(hello.sayHi("Toby") == "HI TOBY")
        assertTrue(hello.sayThankYou("Toby") == "THANK YOU TOBY")
    }
}