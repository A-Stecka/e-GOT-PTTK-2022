package com.example.poapp.model.repository

import androidx.lifecycle.LiveData
import com.example.poapp.model.dao.MountainPassUserDAO
import com.example.poapp.model.entity.MountainPassUser

class MountainPassUserRepository(private val mountainPassDAO: MountainPassUserDAO) {

    fun insert(mountainPass: MountainPassUser): Long {
        return mountainPassDAO.insert(mountainPass)
    }

    fun getAll(): LiveData<List<MountainPassUser>> {
        return mountainPassDAO.getAll()
    }

    fun geMountainPass(id: Int): List<MountainPassUser> {
        return mountainPassDAO.getMountainPass(id)
    }
}