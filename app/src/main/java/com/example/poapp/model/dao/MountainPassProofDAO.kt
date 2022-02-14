package com.example.poapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.poapp.model.entity.MountainPassProof

@Dao
interface MountainPassProofDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mountainPassProof: MountainPassProof)

    @Query("delete from DowodyOdcinkow")
    fun deleteAll()

    @Query("select * from DowodyOdcinkow where FKodcinek=:routeSectionId")
    fun proofsFor(routeSectionId: Long): List<MountainPassProof>

    @Query("select * from DowodyOdcinkow where FKodcinek in (:routeSections)")
    fun proofsFor(routeSections: List<Long>): List<MountainPassProof>

    @Query("select * from DowodyOdcinkow where FKdowod =:proofId")
    fun sectionsFor(proofId: Int): List<MountainPassProof>

    @Query("delete from DowodyOdcinkow where FKdowod = :proofId")
    fun deleteAllForProof(proofId: Int)

    @Query("delete from DowodyOdcinkow where id=:id")
    fun delete(id: Int)

}