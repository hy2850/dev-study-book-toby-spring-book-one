package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User

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

    fun countAll(): Int {
        val c = connectionMaker.makeConnection()

        val ps = c.prepareStatement(
            "select count(*) from users"
        )

        val rs = ps.executeQuery()
        rs.next();
        val count = rs.getInt(1)

        rs.close()
        ps.close()
        c.close()

        return count
    }

    fun deleteAll() {
        val c = connectionMaker.makeConnection()

        val ps = c.prepareStatement(
            "delete from users"
        )

        ps.execute()

        ps.close()
        c.close()
    }
}