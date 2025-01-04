package com.hcpark.springbook.user.dao

import java.sql.Connection

interface ConnectionMaker {

    fun makeConnection(): Connection
}
