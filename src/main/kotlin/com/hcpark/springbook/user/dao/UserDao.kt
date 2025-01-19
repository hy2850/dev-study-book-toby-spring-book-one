package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import com.hcpark.springbook.user.strategy.DeleteAllStatement
import com.hcpark.springbook.user.strategy.StatementStrategy
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

class UserDao(
//    private val jdbcContext: JdbcContext,
) {

    private lateinit var jdbcContext: JdbcContext
    private lateinit var jdbcTemplate: JdbcTemplate

    fun setDataSource(dataSource: DataSource) {
        jdbcContext = JdbcContext().apply {
            setDataSource(dataSource)
        }

        jdbcTemplate = JdbcTemplate(dataSource)
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
            conn.prepareStatement("insert into users(id, name, password, level, loginCnt, recommendCnt) values(?, ?, ?, ?, ?, ?)")
                .apply {
                    setString(1, user.id)
                    setString(2, user.name)
                    setString(3, user.password)
                    setInt(4, user.level.value)
                    setInt(5, user.loginCnt)
                    setInt(6, user.recommendCnt)
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
                    password = rs.getString("password"),
                    level = Level.valueOf(rs.getInt("level")),
                    loginCnt = rs.getInt("loginCnt"),
                    recommendCnt = rs.getInt("recommendCnt"),
                )
            } else {
                throw EmptyResultDataAccessException(1)
            }
        }
    }

    fun getAll(): List<User> {
        return jdbcTemplate.query("select * from users") { rs, _ ->
            User(
                id = rs.getString("id"),
                name = rs.getString("name"),
                password = rs.getString("password"),
                level = Level.valueOf(rs.getInt("level")),
                loginCnt = rs.getInt("loginCnt"),
                recommendCnt = rs.getInt("recommendCnt"),
            )
        }
    }

    fun countAll(): Int {
//        return jdbcContext.workWithStatementStrategyAndResultSet(
//            { connection ->
//                connection.prepareStatement("select count(*) from users")
//            }
//        ) { rs ->
//            rs.next()
//            rs.getInt(1)
//        }

        return jdbcTemplate.queryForObject("select count(*) from users") { rs, _ -> rs.getInt(1) } ?: 0
    }

    fun deleteAll() {
        val strategy: StatementStrategy = DeleteAllStatement()
        jdbcContext.workWithStatementStrategy(strategy)
    }

    fun update(userToUpdate: User) {
        jdbcTemplate.update(
            "update users set name = ?, password = ?, level = ?, loginCnt = ?, recommendCnt = ? where id = ?",
            userToUpdate.name,
            userToUpdate.password,
            userToUpdate.level.value,
            userToUpdate.loginCnt,
            userToUpdate.recommendCnt,
            userToUpdate.id
        )
    }
}