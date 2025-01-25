package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.sql.DataSource
import kotlin.test.Test

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var dao: UserDao

    companion object {
        private val userPark = User("park", "박씨", "123", Level.BASIC, 49, 0)
        private val userKim = User("kim", "김씨", "567", Level.BASIC, 50, 0)
        private val userLee = User("lee", "이씨", "8910", Level.SILVER, 65, 29)
        private val userGo = User("go", "고씨", "111213", Level.SILVER, 65, 30)
        private val userKwon = User("kwon", "권씨", "141516", Level.GOLD, 65, 30)

        private val users: MutableList<User> = mutableListOf(
            userPark,
            userKim,
            userLee,
            userGo,
            userKwon
        )
    }

    @BeforeEach
    fun beforeEach() {
        dao.deleteAll()
        users.forEach(dao::add)
    }

    @Test
    fun isBeanCreated() {
        assertNotNull(userService)
    }

    @Test
    fun upgradeLevel_noUpgrade() {
        val noUpgradeUser = userPark

        userService.upgradeLevel(noUpgradeUser)

        isLevelUpgradedFrom(noUpgradeUser, false)
    }

    @Test
    fun upgradeLevel_doUpgrade() {
        val upgradeUser = userKim

        userService.upgradeLevel(upgradeUser)

        isLevelUpgradedFrom(upgradeUser, true)
    }

    @Test
    fun upgradeLevel_exception() {
        val testUserService = TestUserService(userKim.id, dataSource, dao, UserLevelUpgradePolicyDefault())

        assertDoesNotThrow { testUserService.upgradeLevel(users[0]) }
        assertThrows(TestUserService.TestUserServiceException::class.java) { testUserService.upgradeLevel(users[1]) }
    }

    @Test
    fun upgradeAllLevels() {
        userService.upgradeAllLevels()

        isLevelUpgradedFrom(userPark, false)
        isLevelUpgradedFrom(userKim, true)
        isLevelUpgradedFrom(userLee, false)
        isLevelUpgradedFrom(userGo, true)
        isLevelUpgradedFrom(userKwon, false)
    }

    @Test
    fun upgradeAllLevels_exception() {
        val testUserService = TestUserService(userGo.id, dataSource, dao, UserLevelUpgradePolicyDefault())

        assertThrows(TestUserService.TestUserServiceException::class.java) { testUserService.upgradeAllLevels() }
        isLevelUpgradedFrom(userPark, false)
        isLevelUpgradedFrom(userKim, false) // transaction rollback
        isLevelUpgradedFrom(userLee, false)
    }

//    @Test
//    fun add() {
//        userPark.level = null
//        userService.add(userPark)
//        checkLevel(userPark, Level.BASIC)
//
//        val existingLevel = userLee.level!!
//        userService.add(userLee)
//        checkLevel(userLee, existingLevel)
//    }

    fun isLevelUpgradedFrom(prevUser: User, isUpgraded: Boolean) {
        val updatedUser = dao.get(prevUser.id)
        val prevLevel = prevUser.level
        val expectedLevel = if (isUpgraded) prevLevel.next else prevLevel
        assertEquals(expectedLevel, updatedUser.level)
    }
}