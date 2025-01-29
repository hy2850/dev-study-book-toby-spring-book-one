package com.hcpark.springbook.learningtest.proxy

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import kotlin.test.Test
import kotlin.test.assertEquals

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

    @Test
    fun helloProxyFactoryBeanWithPointcut() {
        val pfBean = ProxyFactoryBean()
        pfBean.setTarget(HelloTarget())

        val pointcut = NameMatchMethodPointcut()
        pointcut.setMappedName("sayH*")

        pfBean.addAdvisor(DefaultPointcutAdvisor(pointcut, UppercaseAdvice()))

        val proxiedHello = pfBean.`object` as Hello

        assertEquals("HELLO TOBY", proxiedHello.sayHello("Toby"))
        assertEquals("HI TOBY", proxiedHello.sayHi("Toby"))
        assertEquals("Thank you Toby", proxiedHello.sayThankYou("Toby"))
    }

    private class UppercaseAdvice : MethodInterceptor {
        override fun invoke(invocation: MethodInvocation): Any {
            val ret = invocation.proceed() as String
            return ret.uppercase()
        }
    }
}