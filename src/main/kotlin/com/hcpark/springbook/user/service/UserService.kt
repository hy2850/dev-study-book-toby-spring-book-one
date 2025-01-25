package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User

open class UserService(
    private val userDao: UserDao,
    private val userLevelUpgradePolicy: UserLevelUpgradePolicy
) {
    fun upgradeAllLevels() {
        val allUsers = userDao.getAll()
        allUsers.forEach { upgradeLevel(it) }
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