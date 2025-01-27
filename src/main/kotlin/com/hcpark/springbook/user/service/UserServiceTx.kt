package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.domain.User
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

class UserServiceTx(
    private val transactionManager: PlatformTransactionManager,
    private val userService: UserService,
) : UserService {
    override fun upgradeAllLevels() {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            userService.upgradeAllLevels()

            transactionManager.commit(status)
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }

    override fun upgradeLevel(user: User) {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            userService.upgradeLevel(user)

            transactionManager.commit(status)
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }
}