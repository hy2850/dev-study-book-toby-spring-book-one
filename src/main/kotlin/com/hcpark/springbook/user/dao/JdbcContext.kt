package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.strategy.StatementStrategy
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class JdbcContext {

    private lateinit var connectionMaker: ConnectionMaker

    fun setConnectionMaker(connectionMaker: ConnectionMaker) {
        this.connectionMaker = connectionMaker
    }

    fun workWithStatementStrategy(strategy: StatementStrategy) {
        var c: Connection? = null
        var ps: PreparedStatement? = null

        try {
            c = connectionMaker.makeConnection()

            ps = strategy.makePreparedStatement(c)

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