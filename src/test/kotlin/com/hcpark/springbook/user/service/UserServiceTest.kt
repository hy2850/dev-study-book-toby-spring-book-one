package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class UserServiceTest {

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

//            User("park", "박씨", "123", Level.BASIC, 49, 0),
//            User("kim", "김씨", "567", Level.BASIC, 50, 0),
//            User("lee", "이씨", "8910", Level.SILVER, 65, 29),
//            User("go", "고씨", "111213", Level.SILVER, 65, 30),
//            User("kwon", "권씨", "141516", Level.GOLD, 65, 30),

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
    fun upgradeLevels() {
        userService.upgradeLevels(users)

        checkLevel(userPark, Level.BASIC)
        checkLevel(userKim, Level.SILVER)
        checkLevel(userLee, Level.SILVER)
        checkLevel(userGo, Level.GOLD)
        checkLevel(userKwon, Level.GOLD)
    }

    fun checkLevel(user: User, expectedLevel: Level) {
        val updatedUser = dao.get(user.id)
        assertEquals(expectedLevel, updatedUser.level)
    }
}