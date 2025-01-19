package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User

class UserService(private val userDao: UserDao) {

    companion object {
        const val MIN_LOGCOUNT_FOR_SILVER = 50
        const val MIN_RECOMMEND_FOR_GOLD = 30
    }

    fun upgradeLevels() {
        val allUsers =  userDao.getAll()

        allUsers.forEach {
            if(canUpgradeLevel(it)) {
                upgradeLevel(it)
            }
        }
    }

    private fun upgradeLevel(user: User) {
        user.upgradeLevel()
        userDao.update(user)
    }

    private fun canUpgradeLevel(user: User): Boolean {
        return when (user.level) {
            Level.BASIC -> user.loginCnt >= MIN_LOGCOUNT_FOR_SILVER
            Level.SILVER -> user.recommendCnt >= MIN_RECOMMEND_FOR_GOLD
            Level.GOLD -> false
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