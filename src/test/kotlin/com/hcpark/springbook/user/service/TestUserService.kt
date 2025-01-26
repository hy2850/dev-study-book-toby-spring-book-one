package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User
import org.springframework.transaction.PlatformTransactionManager

class TestUserService(
    private val exceptionUserId: String,
    transactionManager: PlatformTransactionManager,
    userDao: UserDao,
    userLevelUpgradePolicy: UserLevelUpgradePolicy
) : UserService(transactionManager, userDao, userLevelUpgradePolicy) {

    override fun upgradeLevel(user: User) {
        if (user.id == exceptionUserId) {
            throw TestUserServiceException()
        }
        super.upgradeLevel(user)
    }

    inner class TestUserServiceException : RuntimeException()

}