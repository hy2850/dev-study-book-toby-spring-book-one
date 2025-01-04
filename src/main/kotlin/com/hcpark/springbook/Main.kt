package com.hcpark.springbook

import com.hcpark.springbook.user.dao.*
import com.hcpark.springbook.user.domain.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.AnnotationConfigApplicationContext

@SpringBootApplication
class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args)

//    val daoFactory = DaoFactory() // 직접 만든 IoC 컨테이너
//    val dao = daoFactory.userDao()

//    val ctx = AnnotationConfigApplicationContext(DaoFactory::class.java) // Spring IoC 컨테이너
//    val dao = ctx.getBean("userDao", UserDao::class.java)
//
//    val user1 = User("1", "Kim", "123")
//    dao.add(user1)
//    println("user added, id: ${user1.id}")
//
//    val userFound = dao.get(user1.id)
//    println(userFound)
//
    println("hello world")

//    val countingCtx = AnnotationConfigApplicationContext(CountingDaoFactory::class.java)
//    val daoWithCountingConnection = countingCtx.getBean("userDao", UserDao::class.java)
//
//    val user2 = User("2", "Park", "123")
//    daoWithCountingConnection.add(user2)
//    val userFound = daoWithCountingConnection.get(user2.id)
//    println(userFound)
//
//    val connectionMaker = countingCtx.getBean("connectionMaker", CountingConnectionMaker::class.java)
//    println(connectionMaker.getConnectionCount())
}
