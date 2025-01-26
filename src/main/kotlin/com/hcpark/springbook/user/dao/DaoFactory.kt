package com.hcpark.springbook.user.dao

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DaoFactory {

    @Bean
    fun userDao(): UserDao {
        return UserDao(connectionMaker())
    }

    @Bean
    fun connectionMaker(): ConnectionMaker {
        return H2ConnectionMaker()
    }
}