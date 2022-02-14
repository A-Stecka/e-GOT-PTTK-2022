package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.poapp.model.entity.MountainRange

@Dao
interface MountainRangeDAO {

    @Query("delete from PasmaGorskie")
    fun deleteAll()

    @Query("delete from PasmaGorskie where id=:mountainRangeId")
    fun delete(mountainRangeId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mountainRange: MountainRange): Long

    @Query("select * from PasmaGorskie")
    fun getAll(): LiveData<List<MountainRange>>

    @Query("select * from PasmaGorskie where nazwa = :nameMR")
    fun getMountainRange(nameMR: String): List<MountainRange>

    @Query("select * from PasmaGorskie where id = :idMR")
    fun getMountainRange(idMR: Int): List<MountainRange>
}