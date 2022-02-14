package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poapp.model.entity.UserPoint

@Dao
interface UserPointDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userPoint: UserPoint): Long

    @Update
    fun update(userPoint: UserPoint)

    @Query("select * from PunktyWlasne")
    fun getAll(): LiveData<List<UserPoint>>

    @Query("select * from PunktyWlasne where id = :pointID")
    fun getUserPoint(pointID: Int): List<UserPoint>

    @Query("select * from PunktyWlasne where nazwa = :pointName")
    fun getUserPoint(pointName: String): List<UserPoint>

    @Query("delete from PunktyWlasne")
    fun deleteAll()
}