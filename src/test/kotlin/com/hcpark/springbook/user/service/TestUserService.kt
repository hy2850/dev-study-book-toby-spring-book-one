package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User

class TestUserService(
    private val exceptionUserId: String,
    userDao: UserDao,
    userLevelUpgradePolicy: UserLevelUpgradePolicy
): UserService(userDao, userLevelUpgradePolicy) {

    override fun upgradeLevel(user: User) {
        if (user.id == exceptionUserId) {
            throw TestUserServiceException()
        }
        super.upgradeLevel(user)
    }

    inner class TestUserServiceException : RuntimeException()

}