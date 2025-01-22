package com.hcpark.springbook.user.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.test.Test

class UserTest {

    @Test
    fun upgradeLevel() {
        Level.entries.forEach {
            val prevLevel = it
            val expectedNextLevel = it.next ?: return@forEach

            val user = User(prevLevel)
            val updatedUser = user.upgradeLevel()

            assertEquals(expectedNextLevel, updatedUser.level)
        }
    }

    @Test
    fun cannotUpgradeLevel() {
        Level.entries.forEach {
            val prevLevel = it
            val nextLevel = it.next ?: return

            val user = User(prevLevel)

            assertThrows(IllegalStateException::class.java) { user.upgradeLevel() }
        }
    }
}