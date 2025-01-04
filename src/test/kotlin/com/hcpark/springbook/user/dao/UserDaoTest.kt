package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.dao.EmptyResultDataAccessException

@SpringBootTest // h2 DB 실행을 위해 필요
class UserDaoTest {

    @AfterEach
    fun tearDown() {
        val ctx = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao = ctx.getBean("userDao", UserDao::class.java)
        dao.deleteAll()
    }

    @Test
    fun addAndGet() {
        // given
        val ctx = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao = ctx.getBean("userDao", UserDao::class.java)

        val user1 = User("1", "Kim", "123")
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
        val ctx = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao = ctx.getBean("userDao", UserDao::class.java)

        val user1 = User("1", "Kim", "123")
        dao.add(user1)

        // when, then
        assertThrows(EmptyResultDataAccessException::class.java) { dao.get("invalid_id") }
    }

    @Test
    fun countAll() {
        val ctx = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao = ctx.getBean("userDao", UserDao::class.java)

        assertEquals(0, dao.countAll())

        val user1 = User("1", "Kim", "123")
        dao.add(user1)

        val user2 = User("2", "Park", "123")
        dao.add(user2)

        assertEquals(2, dao.countAll())
    }

    @Test
    fun deleteAll() {
        // given
        val ctx = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao = ctx.getBean("userDao", UserDao::class.java)

        val user1 = User("1", "Kim", "123")
        dao.add(user1)

        val user2 = User("2", "Park", "123")
        dao.add(user2)

        // when
        dao.deleteAll()

        // then
        val afterCount = dao.countAll()
        assertEquals(0, afterCount)
    }
}