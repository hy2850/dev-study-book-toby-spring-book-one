package com.hcpark.springbook.user.dao

import java.sql.Connection

class CountingConnectionMaker(private val realConnectionMaker: ConnectionMaker): ConnectionMaker {

    private var connectionCount = 0

    fun getConnectionCount(): Int {
        return connectionCount
    }

    override fun makeConnection(): Connection {
        connectionCount++
        return realConnectionMaker.makeConnection()
    }
}