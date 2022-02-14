package com.example.poapp.model.repository

import androidx.lifecycle.LiveData
import com.example.poapp.model.dao.UserPointDAO
import com.example.poapp.model.entity.UserPoint

class UserPointRepository(private val userPointDAO: UserPointDAO) {

    fun insert(userPoint: UserPoint): Long {
        return userPointDAO.insert(userPoint)
    }

    fun getAll(): LiveData<List<UserPoint>> {
        return userPointDAO.getAll()
    }

    fun update(userPoint: UserPoint) {
        userPointDAO.update(userPoint)
    }

    fun getUserPoint(id: Int): List<UserPoint> {
        return userPointDAO.getUserPoint(id)
    }

    fun getUserPoint(name: String): List<UserPoint> {
        return userPointDAO.getUserPoint(name)
    }
}