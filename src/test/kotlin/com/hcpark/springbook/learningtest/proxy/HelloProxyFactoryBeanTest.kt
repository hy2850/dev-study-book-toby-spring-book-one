package com.hcpark.springbook.learningtest.proxy

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.aop.framework.ProxyFactoryBean
import kotlin.test.Test

class HelloProxyFactoryBeanTest {

    @Test
    fun helloProxyFactoryBean() {
        val pfBean = ProxyFactoryBean()
        pfBean.setTarget(HelloTarget())
        pfBean.addAdvice(UppercaseAdvice())

        val proxiedHello = pfBean.`object` as Hello

        assertTrue(proxiedHello.sayHello("Toby") == "HELLO TOBY")
        assertTrue(proxiedHello.sayHi("Toby") == "HI TOBY")
        assertTrue(proxiedHello.sayThankYou("Toby") == "THANK YOU TOBY")
    }

    private class UppercaseAdvice : MethodInterceptor {
        override fun invoke(invocation: MethodInvocation): Any {
            val ret = invocation.proceed() as String
            return ret.uppercase()
        }
    }
}