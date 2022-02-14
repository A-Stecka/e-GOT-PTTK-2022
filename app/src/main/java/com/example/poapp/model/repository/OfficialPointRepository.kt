package com.example.poapp.model.repository

import androidx.lifecycle.LiveData
import com.example.poapp.model.dao.OfficialPointDAO
import com.example.poapp.model.entity.OfficialPoint

class OfficialPointRepository(private val officialPointDAO: OfficialPointDAO) {

    fun insert(officialPoint: OfficialPoint): Long {
        return officialPointDAO.insert(officialPoint)
    }

    fun getAll(): LiveData<List<OfficialPoint>> {
        return officialPointDAO.getAll()
    }

    fun update(geoPoint: OfficialPoint) {
        officialPointDAO.update(geoPoint)
    }

    fun getOfficialPoint(id: Int): List<OfficialPoint> {
        return officialPointDAO.getOfficialPoint(id)
    }

    fun getOfficialPoint(name: String): List<OfficialPoint> {
        return officialPointDAO.getOfficialPoint(name)
    }
}