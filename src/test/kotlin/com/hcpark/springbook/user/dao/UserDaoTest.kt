package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
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
        assertEquals(user1.id, userFound.id)
        assertEquals(user1.name, userFound.name)
        assertEquals(user1.password, userFound.password)
    }

    @Test
    fun get_invalidId() {
        // given
        dao.add(user1)

        // when, then
        assertThrows(EmptyResultDataAccessException::class.java) { dao.get("invalid_id") }
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

    companion object {
        private lateinit var user1: User
        private lateinit var user2: User

        @JvmStatic
        @BeforeAll
        fun setup() {
            user1 = User("1", "Kim", "123", Level.SILVER, 1, 2)
            user2 = User("2", "Park", "456", Level.GOLD, 3, 4)
        }
    }
}