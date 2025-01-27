package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User

open class UserServiceImpl(
    private val userDao: UserDao,
    private val userLevelUpgradePolicy: UserLevelUpgradePolicy,
    private val userMailService: UserMailService
) : UserService {
    override fun upgradeAllLevels() {
        val allUsers = userDao.getAll()
        allUsers.forEach { upgradeLevel(it) }
    }

    override fun upgradeLevel(user: User) {
        if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
            val newUser = userLevelUpgradePolicy.upgradeLevel(user)
            userDao.update(newUser)
            userMailService.sendUpgradeEMail(newUser)
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