package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poapp.model.entity.OfficialPoint

@Dao
interface OfficialPointDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(officialPoint: OfficialPoint): Long

    @Update
    fun update(officialPoint: OfficialPoint)

    @Query("select * from PunktyOficjalne")
    fun getAll(): LiveData<List<OfficialPoint>>

    @Query("select * from PunktyOficjalne where id = :pointID")
    fun getOfficialPoint(pointID: Int): List<OfficialPoint>

    @Query("select * from PunktyOficjalne where nazwa = :pointName")
    fun getOfficialPoint(pointName: String): List<OfficialPoint>

    @Query("delete from PunktyOficjalne")
    fun deleteAll()

    @Query("delete from PunktyOficjalne where id=:officialPointId")
    fun delete(officialPointId: Int)

    @Query("select * from PunktyOficjalne where FKpasmoGorskie=:mountainRangeID")
    fun getOfficialPointInRange(mountainRangeID: Int): List<OfficialPoint>

}