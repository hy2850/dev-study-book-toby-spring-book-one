package com.hcpark.springbook

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.domain.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args)

    val dao = UserDao()

    val user1 = User("1", "Kim", "123")
    dao.add(user1)
    println("user added, id: ${user1.id}")

    val userFound = dao.get(user1.id)
    println(userFound)

    println("hello world")
}
