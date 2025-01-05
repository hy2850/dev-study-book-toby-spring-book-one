package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User
import com.hcpark.springbook.user.strategy.DeleteAllStatement
import com.hcpark.springbook.user.strategy.StatementStrategy
import org.springframework.dao.EmptyResultDataAccessException
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class UserDao(private val connectionMaker: ConnectionMaker) {

    fun add(user: User) {
//        try {
//            connectionMaker.makeConnection().use { conn ->
//                conn.prepareStatement(
//                    "insert into users(id, name, password) values(?, ?, ?)"
//                ).use { ps ->
//                    ps.setString(1, user.id)
//                    ps.setString(2, user.name)
//                    ps.setString(3, user.password)
//
//                    ps.executeUpdate()
//                }
//            }
//        } catch (e: SQLException) {
//            throw RuntimeException("Error while accessing data", e)
//        }

//        // 로컬 클래스, 메소드 안에서만 사용 가능
//        class AddStatement: StatementStrategy {
//            override fun makePreparedStatement(connection: Connection): PreparedStatement {
//                val ps = connection.prepareStatement("insert into users(id, name, password) values(?, ?, ?)")
//                ps.setString(1, user.id)
//                ps.setString(2, user.name)
//                ps.setString(3, user.password)
//                return ps
//            }
//        }
//
//        val strategy = AddStatement()
//        jdbcContextWithStatementStrategy(strategy)

        // anonymous class
        jdbcContextWithStatementStrategy(
            object : StatementStrategy {
                override fun makePreparedStatement(connection: Connection): PreparedStatement {
                    val ps = connection.prepareStatement("insert into users(id, name, password) values(?, ?, ?)")
                    ps.setString(1, user.id)
                    ps.setString(2, user.name)
                    ps.setString(3, user.password)
                    return ps
                }
            }
        )
    }

    fun get(id: String): User {
        var c: Connection? = null
        var ps: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            c = connectionMaker.makeConnection()

            ps = c.prepareStatement(
                "select * from users where id = ?"
            )
            ps.setString(1, id)

            rs = ps.executeQuery()

            if (rs.next()) {
                val user = User(
                    id = rs.getString("id"),
                    name = rs.getString("name"),
                    password = rs.getString("password")
                )
                return user
            }

            throw EmptyResultDataAccessException(1)
        } catch (e: SQLException){
            throw e
        }
        finally {
            try {
                ps?.close()
            } catch (_: SQLException) {
            }

            try {
                c?.close()
            } catch (_: SQLException) {
            }

            try {
                rs?.close()
            } catch (_: SQLException) {
            }
        }
    }

    fun countAll(): Int {
        try {
            connectionMaker.makeConnection().use { conn ->
                conn.prepareStatement(
                    "select count(*) from users"
                ).use { ps ->
                    ps.executeQuery().use { rs ->
                        rs.next()
                        return rs.getInt(1)
                    }
                }
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error while accessing data", e)
        }
    }

    fun deleteAll() {
        val strategy: StatementStrategy = DeleteAllStatement()
        jdbcContextWithStatementStrategy(strategy)
    }

    fun jdbcContextWithStatementStrategy(strategy: StatementStrategy) {
        var c: Connection? = null
        var ps: PreparedStatement? = null

        try {
            c = connectionMaker.makeConnection()

            val ps = strategy.makePreparedStatement(c)

            ps.execute()
        } catch (e: SQLException){
            throw e
        }
        finally {
            try {
                ps?.close()
            } catch (_: SQLException) {
            }

            try {
                c?.close()
            } catch (_: SQLException) {
            }
        }
    }
}