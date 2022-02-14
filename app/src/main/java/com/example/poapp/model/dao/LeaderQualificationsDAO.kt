package com.example.poapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.poapp.model.entity.LeaderQualifications

@Dao
interface LeaderQualificationsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(leaderQualifications: LeaderQualifications): Long

    @Query("delete from UprawnieniaPrzodownikow")
    fun deleteAll()

    @Query("select * from UprawnieniaPrzodownikow where FKprzodownik = :leaderId")
    fun getQualifications(leaderId: Int): List<LeaderQualifications>
}

