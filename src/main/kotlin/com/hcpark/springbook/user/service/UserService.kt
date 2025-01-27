package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.domain.User

interface UserService {

    fun upgradeAllLevels()

    fun upgradeLevel(user: User)
}