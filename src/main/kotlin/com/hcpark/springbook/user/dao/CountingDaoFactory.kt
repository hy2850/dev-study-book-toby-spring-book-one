package com.hcpark.springbook.user.dao

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CountingDaoFactory {

    @Bean
    fun userDao(): UserDao {
        return UserDao(connectionMaker())
    }

    @Bean
    fun connectionMaker(): ConnectionMaker {
        return CountingConnectionMaker(realConnectionMaker())
    }

    @Bean
    fun realConnectionMaker(): ConnectionMaker {
        return CountingConnectionMaker(H2ConnectionMaker())
    }
}