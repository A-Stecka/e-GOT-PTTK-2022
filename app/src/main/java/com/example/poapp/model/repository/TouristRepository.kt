package com.example.poapp.model.repository

import com.example.poapp.model.dao.TouristDAO
import com.example.poapp.model.entity.Tourist

class TouristRepository(private val touristDAO: TouristDAO) {

    fun getTourist(id: Long): Tourist {
        return touristDAO.getTourist(id)[0]
    }
}