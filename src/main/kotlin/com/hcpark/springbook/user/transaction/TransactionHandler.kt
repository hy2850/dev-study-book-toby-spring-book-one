package com.hcpark.springbook.user.transaction

import com.hcpark.springbook.user.service.UserService
import org.springframework.cglib.proxy.InvocationHandler
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class TransactionHandler(
    private val transactionManager: PlatformTransactionManager,
    private val userService: UserService
) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            val res = method.invoke(userService, *args)

            transactionManager.commit(status)

            return res
        } catch (e: InvocationTargetException) {
            transactionManager.rollback(status)
            throw e
        }
    }
}