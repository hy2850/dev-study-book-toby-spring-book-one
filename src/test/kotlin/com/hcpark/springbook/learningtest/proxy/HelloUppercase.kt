package com.hcpark.springbook.learningtest.proxy

class HelloUppercase(
    private val target: Hello
) : Hello {
    override fun sayHello(name: String): String {
        return target.sayHello(name).uppercase()
    }

    override fun sayHi(name: String): String {
        return target.sayHi(name).uppercase()
    }

    override fun sayThankYou(name: String): String {
        return target.sayThankYou(name).uppercase()
    }
}