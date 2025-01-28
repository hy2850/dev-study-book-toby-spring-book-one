package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User

class UserDaoMock : UserDao {

    val users = mutableListOf<User>()
    val updatedUsers = mutableListOf<User>()

    fun setUsers(vararg users: User) {
        this.users.addAll(users)
    }

    override fun getAll(): List<User> {
        return users
    }

    override fun update(userToUpdate: User) {
        updatedUsers.add(userToUpdate)
    }

    override fun add(user: User) {
        throw UnsupportedOperationException()
    }

    override fun get(id: String): User {
        throw UnsupportedOperationException()
    }

    override fun countAll(): Int {
        throw UnsupportedOperationException()
    }

    override fun deleteAll() {
        throw UnsupportedOperationException()
    }
}