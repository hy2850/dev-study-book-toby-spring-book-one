package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun isBeanCreated() {
        assertNotNull(userService)
    }
}