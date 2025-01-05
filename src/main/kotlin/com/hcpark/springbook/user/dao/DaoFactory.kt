package com.hcpark.springbook.user.dao

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DaoFactory {

    @Bean
    fun userDao(): UserDao {
        return UserDao(jdbcContext())
    }

    @Bean
    fun jdbcContext(): JdbcContext {
        val jdbcContext = JdbcContext()
        jdbcContext.setConnectionMaker(connectionMaker())
        return jdbcContext
    }

    @Bean
    fun connectionMaker(): ConnectionMaker {
        return H2ConnectionMaker()
    }
}