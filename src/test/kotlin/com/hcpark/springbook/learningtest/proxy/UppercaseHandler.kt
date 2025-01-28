package com.hcpark.springbook.learningtest.proxy

import org.springframework.cglib.proxy.InvocationHandler
import java.lang.reflect.Method

class UppercaseHandler(
    private val target: Any
) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any {
        val ret: Any = method.invoke(target, *args)
        if (ret is String && method.name.startsWith("sayH")) {
            return ret.uppercase()
        }
        return ret
    }
}