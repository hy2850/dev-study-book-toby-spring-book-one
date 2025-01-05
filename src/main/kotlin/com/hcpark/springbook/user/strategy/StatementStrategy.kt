package com.hcpark.springbook.user.strategy

import java.sql.Connection
import java.sql.PreparedStatement

fun interface StatementStrategy {

    fun makePreparedStatement(connection: Connection): PreparedStatement
}