package com.hcpark.springbook.user.transaction

import com.hcpark.springbook.user.service.UserService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.Test

@SpringBootTest
class TxProxyFactoryBeanTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun txProxyFactoryBean() {
        val txUserServiceProxyFactoryBean = applicationContext.getBean("&userService", ProxyFactoryBean::class.java)
        assertNotNull(txUserServiceProxyFactoryBean)
        assertTrue(txUserServiceProxyFactoryBean is ProxyFactoryBean)
    }

    @Test
    fun UserServiceBean() {
        val txUserService = applicationContext.getBean("userService", UserService::class.java)
        assertNotNull(txUserService)
        assertTrue(txUserService is UserService)
    }
}