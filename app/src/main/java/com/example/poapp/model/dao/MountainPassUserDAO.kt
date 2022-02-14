package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poapp.model.entity.MountainPassUser

@Dao
interface MountainPassUserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mountainPassUser: MountainPassUser): Long

    @Query("delete from OdcinkiWlasne")
    fun deleteAll()

    @Query("select * from OdcinkiWlasne")
    fun getAll(): LiveData<List<MountainPassUser>>

    @Query("select * from OdcinkiWlasne where id = :passId")
    fun getMountainPass(passId: Int): List<MountainPassUser>

}