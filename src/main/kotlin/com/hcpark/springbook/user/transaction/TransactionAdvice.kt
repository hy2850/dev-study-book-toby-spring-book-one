package com.hcpark.springbook.user.transaction

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

// Advice는 타겟 정보를 가지지 않는다! -> 싱글톤 등록 가능
class TransactionAdvice(
    private val transactionManager: PlatformTransactionManager,
) : MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any? {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            val res = invocation.proceed()
            transactionManager.commit(status)
            return res
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }
}