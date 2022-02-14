package com.example.poapp.model.repository

import com.example.poapp.model.dao.LeaderQualificationsDAO
import com.example.poapp.model.entity.LeaderQualifications

class LeaderQualificationsRepository(private val leaderQualificationsDAO: LeaderQualificationsDAO) {

    fun getQualificationsForLeader(leaderId: Int): List<LeaderQualifications> {
        return leaderQualificationsDAO.getQualifications(leaderId)
    }
}