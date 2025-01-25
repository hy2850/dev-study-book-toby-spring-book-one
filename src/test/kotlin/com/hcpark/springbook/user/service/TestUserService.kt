package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User
import javax.sql.DataSource

class TestUserService(
    private val exceptionUserId: String,
    dataSource: DataSource,
    userDao: UserDao,
    userLevelUpgradePolicy: UserLevelUpgradePolicy
): UserService(dataSource, userDao, userLevelUpgradePolicy) {

    override fun upgradeLevel(user: User) {
        if (user.id == exceptionUserId) {
            throw TestUserServiceException()
        }
        super.upgradeLevel(user)
    }

    inner class TestUserServiceException : RuntimeException()

}