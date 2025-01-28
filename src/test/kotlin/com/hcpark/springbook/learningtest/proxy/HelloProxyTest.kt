package com.hcpark.springbook.learningtest.proxy

import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.cglib.proxy.Proxy
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

    @Test
    fun HelloDynamicProxyTest() {
        val helloDynamicProxy = Proxy.newProxyInstance(
            javaClass.classLoader,
            arrayOf(Hello::class.java),
            UppercaseHandler(HelloTarget())
        ) as Hello

        assertTrue(helloDynamicProxy.sayHello("Toby") == "HELLO TOBY")
        assertTrue(helloDynamicProxy.sayHi("Toby") == "HI TOBY")
//        assertTrue(helloDynamicProxy.sayThankYou("Toby") == "THANK YOU TOBY")
        assertTrue(helloDynamicProxy.sayThankYou("Toby") == "Thank you Toby")
    }
}