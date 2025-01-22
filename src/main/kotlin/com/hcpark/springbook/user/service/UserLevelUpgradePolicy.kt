package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.domain.User

interface UserLevelUpgradePolicy {
    companion object {
        const val MIN_LOGCOUNT_FOR_SILVER = 50
        const val MIN_RECOMMEND_FOR_GOLD = 30
    }

    fun upgradeLevel(user: User): User
    fun canUpgradeLevel(user: User): Boolean
}