package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.dao.EmptyResultDataAccessException

@SpringBootTest // h2 DB 실행을 위해 필요
class UserDaoTest {

    @Autowired
    private lateinit var ctx: ApplicationContext

    @Autowired
    private lateinit var dao: UserDao

    @BeforeEach
    fun beforeEach() {
        println(this.ctx)
        println(this)
    }

    @AfterEach
    fun tearDown() {
        dao.deleteAll()
    }

    @Test
    fun addAndGet() {
        // given
        dao.add(user1)

        // when
        val userFound = dao.get(user1.id)

        // then
        checkSameUser(userFound, user1)
    }

    @Test
    fun get_invalidId() {
        // given
        dao.add(user1)

        // when, then
        assertThrows(EmptyResultDataAccessException::class.java) { dao.get("invalid_id") }
    }

    @Test
    fun getAll() {
        // given
        dao.add(user1)
        dao.add(user2)

        // when
        val usersFound = dao.getAll()
        val idsFound = usersFound.map(User::id).toSet()

        // then
        assertTrue(idsFound.contains(user1.id))
        assertTrue(idsFound.contains(user2.id))
    }

    @Test
    fun countAll() {
        assertEquals(0, dao.countAll())

        dao.add(user1)
        dao.add(user2)

        assertEquals(2, dao.countAll())
    }

    @Test
    fun deleteAll() {
        // given
        dao.add(user1)
        dao.add(user2)

        // when
        dao.deleteAll()

        // then
        val afterCount = dao.countAll()
        assertEquals(0, afterCount)
    }

    @Test
    fun update() {
        // given
        dao.add(user1)
        dao.add(user2)

        val id = user1.id

        val newName = "Lee"
        val newPassword = "999"
        val newLevel = Level.BASIC
        val newLoginCnt = 99
        val newRecommendCnt = 88
        val newUser = User(id, newName, newPassword, newLevel, newLoginCnt, newRecommendCnt)

        // when
        dao.update(newUser)

        // then
        val userFound = dao.get(id)
        checkSameUser(userFound, newUser)

        val notUpdatedUserFound = dao.get(user2.id)
        checkSameUser(notUpdatedUserFound, user2)
    }

    private fun checkSameUser(user1: User, user2: User) {
        assertEquals(user1.id, user2.id)
        assertEquals(user1.name, user2.name)
        assertEquals(user1.password, user2.password)
        assertEquals(user1.level, user2.level)
        assertEquals(user1.loginCnt, user2.loginCnt)
        assertEquals(user1.recommendCnt, user2.recommendCnt)
    }

    companion object {
        private lateinit var user1: User
        private lateinit var user2: User

        @JvmStatic
        @BeforeAll
        fun setup() {
            user1 = User("1", "Kim", "123", Level.SILVER, 1, 2, "kim@gmail.com")
            user2 = User("2", "Park", "456", Level.GOLD, 3, 4, "park@gmail.com")
        }
    }
}