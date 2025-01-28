package com.hcpark.springbook.learningtest.proxy

class HelloTarget : Hello {

    override fun sayHello(name: String): String {
        return "Hello $name"
    }

    override fun sayHi(name: String): String {
        return "Hi $name"
    }

    override fun sayThankYou(name: String): String {
        return "Thank you $name"
    }
}