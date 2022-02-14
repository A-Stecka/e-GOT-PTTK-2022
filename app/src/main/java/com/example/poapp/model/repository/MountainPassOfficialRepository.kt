package com.example.poapp.model.repository

import androidx.lifecycle.LiveData
import com.example.poapp.model.dao.MountainPassOfficialDAO
import com.example.poapp.model.entity.MountainPassOfficial

class MountainPassOfficialRepository(private val mountainPassDAO: MountainPassOfficialDAO) {

    fun insert(mountainPass: MountainPassOfficial): Long {
        return mountainPassDAO.insert(mountainPass)
    }

    fun getAll(): LiveData<List<MountainPassOfficial>> {
        return mountainPassDAO.getAll()
    }

    fun geMountainPass(id: Int): List<MountainPassOfficial> {
        return mountainPassDAO.getMountainPass(id)
    }

    fun update(mountainPass: MountainPassOfficial) {
        mountainPassDAO.update(mountainPass)
    }

    fun getAllActive(): LiveData<List<MountainPassOfficial>> {
        return mountainPassDAO.getAllWithStatus("aktywny")
    }
}