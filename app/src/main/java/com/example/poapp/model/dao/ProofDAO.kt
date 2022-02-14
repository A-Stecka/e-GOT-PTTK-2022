package com.example.poapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.poapp.model.entity.Proof

@Dao
interface ProofDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(proof: Proof): Long

    @Query("delete from Dowody")
    fun deleteAll()

    @Query("delete from Dowody where id = :proofID")
    fun delete(proofID: Long)

    @Query("select * from Dowody")
    fun getAll(): LiveData<List<Proof>>

    @Query("select * from Dowody where id = :proofID")
    fun getProof(proofID: Long): Proof
}