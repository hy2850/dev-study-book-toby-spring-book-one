package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User

class UserService(private val userDao: UserDao) {

    companion object {
        const val MIN_LOGCOUNT_FOR_SILVER = 50
        const val MIN_RECOMMEND_FOR_GOLD = 30
    }

    fun upgradeLevels(users: List<User>) {
        users.forEach {
            var isChanged = false
            if (it.level == Level.BASIC && it.loginCnt >= MIN_LOGCOUNT_FOR_SILVER) {
                it.level = Level.SILVER
                isChanged = true
            } else if (it.level == Level.SILVER && it.recommendCnt >= MIN_RECOMMEND_FOR_GOLD) {
                it.level = Level.GOLD
                isChanged = true
            }

            if (isChanged) {
                userDao.update(it)
            }
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