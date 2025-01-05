package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User
import com.hcpark.springbook.user.strategy.DeleteAllStatement
import com.hcpark.springbook.user.strategy.StatementStrategy
import org.springframework.dao.EmptyResultDataAccessException

class UserDao(
//    private val jdbcContext: JdbcContext,
) {

    private lateinit var jdbcContext: JdbcContext

    fun setConectionMaker(connectionMaker: ConnectionMaker) {
        jdbcContext = JdbcContext().apply {
            setConnectionMaker(connectionMaker)
        }
    }

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
//        jdbcContext.workWithStatementStrategy(strategy)

        // anonymous class
//        jdbcContext.workWithStatementStrategy(
//            StatementStrategy { connection ->
//                    val ps = connection.prepareStatement("insert into users(id, name, password) values(?, ?, ?)")
//                    ps.setString(1, user.id)
//                    ps.setString(2, user.name)
//                    ps.setString(3, user.password)
//                    return ps
//            }
//        )

        // functional interface
        jdbcContext.workWithStatementStrategy { conn ->
            conn.prepareStatement("insert into users(id, name, password) values(?, ?, ?)")
                .apply {
                    setString(1, user.id)
                    setString(2, user.name)
                    setString(3, user.password)
                }
        }
    }

    fun get(id: String): User {
        return jdbcContext.workWithStatementStrategyAndResultSet(
            { conn ->
                conn.prepareStatement("select * from users where id = ?")
                    .apply {
                        setString(1, id)
                    }
            }
        ) { rs ->
            if (rs.next()) {
                User(
                    id = rs.getString("id"),
                    name = rs.getString("name"),
                    password = rs.getString("password")
                )
            } else {
                throw EmptyResultDataAccessException(1)
            }
        }
    }

    fun countAll(): Int {
        return jdbcContext.workWithStatementStrategyAndResultSet(
            { connection ->
                connection.prepareStatement("select count(*) from users")
            }
        ) { rs ->
            rs.next()
            rs.getInt(1)
        }
    }

    fun deleteAll() {
        val strategy: StatementStrategy = DeleteAllStatement()
        jdbcContext.workWithStatementStrategy(strategy)
    }
}