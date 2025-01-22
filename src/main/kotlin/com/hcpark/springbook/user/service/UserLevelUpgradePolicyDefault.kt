package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import com.hcpark.springbook.user.service.UserLevelUpgradePolicy.Companion.MIN_LOGCOUNT_FOR_SILVER
import com.hcpark.springbook.user.service.UserLevelUpgradePolicy.Companion.MIN_RECOMMEND_FOR_GOLD

class UserLevelUpgradePolicyDefault: UserLevelUpgradePolicy {

    override fun upgradeLevel(user: User): User {
        return user.upgradeLevel()
    }

    override fun canUpgradeLevel(user: User): Boolean {
        return when (user.level) {
            Level.BASIC -> user.loginCnt >= MIN_LOGCOUNT_FOR_SILVER
            Level.SILVER -> user.recommendCnt >= MIN_RECOMMEND_FOR_GOLD
            Level.GOLD -> false
        }
    }
}