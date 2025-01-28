package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.User

interface UserDao {
    fun add(user: User)
    fun get(id: String): User
    fun getAll(): List<User>
    fun countAll(): Int
    fun deleteAll()
    fun update(userToUpdate: User)
}