package com.hcpark.springbook.learningtest.factorybean

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.Test

@SpringBootTest(classes = [FactoryBeanTestConfig::class])
class MessageBeanTest {

    @Autowired
    private lateinit var context: ApplicationContext

    @Test
    fun getMessageFromFactoryBean() {
        val message = context.getBean(Message::class.java)
        assertEquals(message.text, "Factory Bean")

        val factory = context.getBean("&message")
        assertTrue(factory is MessageFactoryBean)
    }
}