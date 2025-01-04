package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext

@SpringBootTest // h2 DB 실행을 위해 필요
class UserDaoTest {

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

        val beforeCount = dao.countAll()
        assertEquals(2, beforeCount)

        // when
        dao.deleteAll()

        // then
        val afterCount = dao.countAll()
        assertEquals(0, afterCount)
    }
}