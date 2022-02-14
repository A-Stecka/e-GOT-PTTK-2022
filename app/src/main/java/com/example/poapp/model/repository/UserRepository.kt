package com.example.poapp.model.repository

import androidx.lifecycle.LiveData
import com.example.poapp.model.dao.UserDAO
import com.example.poapp.model.entity.User

class UserRepository(private val userDao: UserDAO) {

    fun insert(user: User): Long {
        return userDao.insert(user)
    }

    fun update(user: User) {
        userDao.update(user)
    }

    fun delete(user: User) {
        userDao.delete(user)
    }

    fun deleteAll() {
        userDao.deleteAll()
    }

    fun getAll(): LiveData<List<User>> {
        return userDao.getAll()
    }

    fun getUser(id: Long): List<User> {
        return userDao.getUser(id)
    }
}