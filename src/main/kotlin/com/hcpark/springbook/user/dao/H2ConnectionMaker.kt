package com.hcpark.springbook.user.dao

import java.sql.Connection
import java.sql.DriverManager

class H2ConnectionMaker: ConnectionMaker {

    override fun makeConnection(): Connection {
        Class.forName("org.h2.Driver")
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")
    }
}