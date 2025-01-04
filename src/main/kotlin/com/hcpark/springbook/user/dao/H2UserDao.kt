package com.hcpark.springbook.user.dao

import java.sql.Connection
import java.sql.DriverManager

class H2UserDao: UserDao() {
    override fun connection(): Connection {
        Class.forName("org.h2.Driver")
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")
    }
}