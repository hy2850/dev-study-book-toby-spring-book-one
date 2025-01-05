package com.hcpark.springbook.user.dao

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DaoFactory {

    @Bean
    fun userDao(): UserDao {
//        return UserDao(jdbcContext())
        return UserDao().apply {
            setConectionMaker(connectionMaker())
        }
    }

//    @Bean
//    fun jdbcContext(): JdbcContext {
//        val jdbcContext = JdbcContext()
//        jdbcContext.setConnectionMaker(connectionMaker())
//        return jdbcContext
//    }

    // DataSource 생성하면 data.sql로 초기화하는 작업이 안돼서 USERS 테이블이 없음
//    @Bean
//    fun dataSource(): DataSource {
//        return SimpleDriverDataSource().apply {
//            setDriverClass(org.h2.Driver::class.java)
//            url = "jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM 'classpath:data.sql'"
//            username = "sa"
//            password = ""
//        }
//    }

    @Bean
    fun connectionMaker(): ConnectionMaker {
        return H2ConnectionMaker()
    }
}