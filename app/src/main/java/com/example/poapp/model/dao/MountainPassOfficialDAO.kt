package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poapp.model.entity.MountainPassOfficial

@Dao
interface MountainPassOfficialDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mountainPassOfficial: MountainPassOfficial): Long

    @Update
    fun update(mountainPassOfficial: MountainPassOfficial)

    @Query("delete from OdcinkiOficjalne")
    fun deleteAll()

    @Query("select * from OdcinkiOficjalne")
    fun getAll(): LiveData<List<MountainPassOfficial>>

    @Query("select * from OdcinkiOficjalne where status=:status")
    fun getAllWithStatus(status: String): LiveData<List<MountainPassOfficial>>

    @Query("select * from OdcinkiOficjalne where id = :passId")
    fun getMountainPass(passId: Int): List<MountainPassOfficial>
}