package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User
import org.springframework.transaction.PlatformTransactionManager

class TestExceptionUserServiceMock(
    transactionManager: PlatformTransactionManager,
    userDao: UserDao,
    userLevelUpgradePolicy: UserLevelUpgradePolicy,
    userMailService: UserMailService
) : UserService(transactionManager, userDao, userLevelUpgradePolicy, userMailService) {

    private var exceptionUserId: String = ""

    fun setExceptionUserId(exceptionUserId: String) {
        this.exceptionUserId = exceptionUserId
    }

    override fun upgradeLevel(user: User) {
        if (user.id == exceptionUserId) {
            throw TestUserServiceException()
        }
        super.upgradeLevel(user)
    }

    inner class TestUserServiceException : RuntimeException()

}