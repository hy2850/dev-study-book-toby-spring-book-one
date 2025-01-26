package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User
import java.sql.Connection
import java.sql.DriverManager

class UserDao(private val connectionMaker: ConnectionMaker) {

    fun add(user: User) {
        val c = connectionMaker.makeConnection()

        val ps = c.prepareStatement(
            "insert into users(id, name, password) values(?, ?, ?)"
        )
        ps.setString(1, user.id)
        ps.setString(2, user.name)
        ps.setString(3, user.password)

        ps.executeUpdate()

        ps.close()
        c.close()
    }

    fun get(id: String): User {
        val c = connectionMaker.makeConnection()

        val ps = c.prepareStatement(
            "select * from users where id = ?"
        )
        ps.setString(1, id)

        val rs = ps.executeQuery()
        rs.next();
        val user = User(
            id = rs.getString("id"),
            name = rs.getString("name"),
            password = rs.getString("password")
        )

        rs.close()
        ps.close()
        c.close()

        return user
    }

    private fun connection(): Connection {
        Class.forName("org.h2.Driver")
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")
    }
}