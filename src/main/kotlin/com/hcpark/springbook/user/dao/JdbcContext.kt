package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.strategy.StatementStrategy
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource

class JdbcContext {

    private lateinit var dataSource: DataSource

    fun setDataSource(dataSource: DataSource) {
        this.dataSource = dataSource
    }

    fun executeSQL(sql: String) {
        workWithStatementStrategy(object : StatementStrategy {
            override fun makePreparedStatement(connection: Connection): PreparedStatement {
                return connection.prepareStatement(sql)
            }
        })
    }

    fun workWithStatementStrategy(strategy: StatementStrategy) {
        commonExceptionHandlerTemplate<Boolean>(strategy)
    }

    fun <T> workWithStatementStrategyAndResultSet(strategy: StatementStrategy, resultSetHandler: (ResultSet) -> T): T {
        return commonExceptionHandlerTemplate(strategy, resultSetHandler)
    }

    private fun <T> commonExceptionHandlerTemplate(
        strategy: StatementStrategy,
        resultSetHandler: ((ResultSet) -> T)? = null
    ): T {
        var c: Connection? = null
        var ps: PreparedStatement? = null

        try {
            c = dataSource.connection

            ps = strategy.makePreparedStatement(c)

//            // 1. just execute, return nothing
//            ps.execute()
//
//            // 2. execute and handle ResultSet
//            val rs = ps.executeQuery()
//            rs.use {
//                // return result
//            }

            return if (resultSetHandler != null) {
                ps.executeQuery().use { rs ->
                    resultSetHandler(rs)
                }
            }
            else {
                ps.execute() as T
            }

//            return resultSetHandler?.let { return ps.executeQuery().use(it) } ?: ps.execute() as T
        } catch (e: SQLException){
            throw RuntimeException("Error while accessing the database", e)
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