package com.hcpark.springbook.user.dao

class DaoFactory {

    fun getUserDao(): UserDao {
        return UserDao(getConnectionMaker())
    }

    private fun getConnectionMaker(): ConnectionMaker {
        return H2ConnectionMaker()
    }
}