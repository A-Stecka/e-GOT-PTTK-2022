package com.example.poapp.model.repository

import com.example.poapp.model.dao.LeaderDAO
import com.example.poapp.model.entity.Leader
import com.example.poapp.model.entity.User

class LeaderRepository(private val leaderDAO: LeaderDAO) {

    fun getLeader(leaderId: Long): Leader? {
        val list = leaderDAO.getLeader(leaderId.toInt())
        return if (list.isEmpty()) {
            null
        } else {
            list[0]
        }
    }

    fun getLeaderUser(leaderId: Long): User? {
        return getLeader(leaderId)?.FKturysta?.let { leaderDAO.getUserLeader(it) }?.get(0)
    }

}