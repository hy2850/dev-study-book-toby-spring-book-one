package com.hcpark.springbook.user.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.test.Test

class UserTest {

    private val user = User()

    @Test
    fun upgradeLevel() {
        Level.entries.forEach {
            val prevLevel = it
            val nextLevel = it.next ?: return

            user.level = prevLevel
            user.upgradeLevel()

            assertEquals(nextLevel, user.level)
        }
    }

    @Test
    fun cannotUpgradeLevel() {
        Level.entries.forEach {
            val prevLevel = it
            val nextLevel = it.next

            if (nextLevel != null) {
                return
            }

            user.level = prevLevel
            assertThrows(IllegalStateException::class.java) { user.upgradeLevel() }
        }
    }
}