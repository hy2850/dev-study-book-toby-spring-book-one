package com.hcpark.springbook.user.transaction

import org.springframework.cglib.proxy.InvocationHandler
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class TransactionHandler(
    private val target: Any,
    private val transactionManager: PlatformTransactionManager,
    private val pattern: String
) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any {
        if (method.name.startsWith(pattern)) {
            return invokeWithTransaction(method, args)
        }
        return method.invoke(target, *args)
    }

    private fun invokeWithTransaction(method: Method, args: Array<out Any>): Any {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            val res = method.invoke(target, *args)
            transactionManager.commit(status)
            return res
        } catch (e: InvocationTargetException) {
            transactionManager.rollback(status)
            throw e
        }
    }
}