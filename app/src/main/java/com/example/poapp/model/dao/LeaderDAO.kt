package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poapp.model.entity.Leader
import com.example.poapp.model.entity.User

@Dao
interface LeaderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(leader: Leader): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(leader: Leader)

    @Query("select * from Przodownicy where nrLegitymacji = :leaderId")
    fun getLeader(leaderId: Int): List<Leader>

    @Delete
    fun delete(tourist: Leader)

    @Query("delete from Przodownicy")
    fun deleteAll()

    @Query("select * from Przodownicy")
    fun getAll(): LiveData<List<Leader>>

    @Query("select * from Uzytkownicy where id = :userId")
    fun getUserLeader(userId: Int): List<User>
}