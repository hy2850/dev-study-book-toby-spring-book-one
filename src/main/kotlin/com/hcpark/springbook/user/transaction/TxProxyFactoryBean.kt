package com.hcpark.springbook.user.transaction

import org.springframework.beans.factory.FactoryBean
import org.springframework.cglib.proxy.Proxy
import org.springframework.transaction.PlatformTransactionManager

class TxProxyFactoryBean(
    private val target: Any,
    private val transactionManager: PlatformTransactionManager,
    private val pattern: String,
    private val serviceInterface: Class<out Any>
) : FactoryBean<Any> {

    override fun getObject(): Any? {
        return Proxy.newProxyInstance(
            javaClass.classLoader,
            arrayOf(serviceInterface),
            TransactionHandler(target, transactionManager, pattern)
        )
    }

    override fun getObjectType(): Class<*> {
        return serviceInterface
    }
}