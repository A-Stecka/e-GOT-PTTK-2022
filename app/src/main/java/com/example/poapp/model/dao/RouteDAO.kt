package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.poapp.model.entity.Route

@Dao
interface RouteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(route: Route): Long

    @Query("select * from Trasy where FKturysta = :touristId ORDER BY id DESC")
    fun getAll(touristId: Int): LiveData<List<Route>>

    @Query("select * from Trasy")
    fun getAll(): List<Route>

    @Query("delete from Trasy")
    fun deleteAll()

    @Query("select * from Trasy where id = :routeID")
    fun getRoute(routeID: Int): List<Route>

    @Update
    fun update(route: Route)

    @Query("delete from Trasy where id = :routeId")
    fun delete(routeId: Long)

    @Query("select * from Trasy where status = :status")
    fun getAllWithStatus(status: String): List<Route>

}