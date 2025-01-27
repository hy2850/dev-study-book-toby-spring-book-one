package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.service.UserLevelUpgradePolicyDefault
import com.hcpark.springbook.user.service.UserMailService
import com.hcpark.springbook.user.service.UserService
import com.hcpark.springbook.user.service.UserServiceImpl
import com.hcpark.springbook.user.service.UserServiceTx
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class DaoFactory {

    @Bean
    fun userDao(dataSource: DataSource): UserDao {
        return UserDao().apply {
            setDataSource(dataSource)
        }
    }

    @Bean
    fun userService(dataSource: DataSource, mailSender: MailSender): UserService {
        val userService = UserServiceImpl(
            userDao(dataSource),
            UserLevelUpgradePolicyDefault(),
            userMailService(mailSender)
        )

        return UserServiceTx(
            platformTransactionManager(dataSource),
            userService
        )
    }

    @Bean
    fun platformTransactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    fun userMailService(mailSender: MailSender): UserMailService {
        return UserMailService(mailSender)
    }

    @Bean
    fun mailSender(): MailSender {
        return JavaMailSenderImpl()
    }

//    @Bean
//    fun jdbcContext(): JdbcContext {
//        val jdbcContext = JdbcContext()
//        jdbcContext.setConnectionMaker(connectionMaker())
//        return jdbcContext
//    }

    // DataSource 다시 생성하지 말기, 이미 application.yaml 설정 파일 기반으로 하나 생성된 상태라서 충돌 일어남
//    @Bean
//    fun dataSource(): DataSource {
//        val dataSource = SimpleDriverDataSource().apply {
//            setDriverClass(org.h2.Driver::class.java)
//            url = "jdbc:h2:mem:testdb"
//            username = "sa"
//            password = ""
//        }
//
//        dataSource.connection.use {
//            it.createStatement().use { stmt ->
//                stmt.executeUpdate("CREATE TABLE USERS (ID VARCHAR(10) PRIMARY KEY, NAME VARCHAR(20), PASSWORD VARCHAR(10))")
//            }
//        }
//
//        return dataSource
//    }

//    @Bean
//    fun connectionMaker(): ConnectionMaker {
//        var template = JdbcTemplate(SimpleDriverDataSource())
//        template.update("hello")

//        return H2ConnectionMaker()
//    }
}