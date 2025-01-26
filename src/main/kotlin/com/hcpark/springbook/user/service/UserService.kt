package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

open class UserService(
    private val transactionManager: PlatformTransactionManager,
    private val userDao: UserDao,
    private val userLevelUpgradePolicy: UserLevelUpgradePolicy
) {
    fun upgradeAllLevels() {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            val allUsers = userDao.getAll()
            allUsers.forEach { upgradeLevel(it) }

            transactionManager.commit(status)
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }

    internal open fun upgradeLevel(user: User) {
        if(userLevelUpgradePolicy.canUpgradeLevel(user)) {
            val newUser = userLevelUpgradePolicy.upgradeLevel(user)
            userDao.update(newUser)
        }
    }

    fun add(user: User) {
        // Kotlin에선 컴파일타임 nullability 체크 가능
//        if (user.level == null) {
//            user.level = Level.BASIC
//        }

        userDao.add(user)
    }
}