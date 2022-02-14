package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poapp.model.entity.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: User)

    @Query("select * from Uzytkownicy where id = :userId")
    fun getUser(userId: Long): List<User>

    @Delete
    fun delete(user: User)

    @Query("delete from Uzytkownicy")
    fun deleteAll()

    @Query("select * from Uzytkownicy")
    fun getAll(): LiveData<List<User>>
}