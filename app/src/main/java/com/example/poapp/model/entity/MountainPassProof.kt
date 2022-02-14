package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "DowodyOdcinkow", foreignKeys = [
        ForeignKey(
            entity = RouteSection::class,
            parentColumns = ["id"],
            childColumns = ["FKodcinek"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Proof::class,
            parentColumns = ["id"],
            childColumns = ["FKdowod"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class MountainPassProof(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val FKodcinek: Int,
    val FKdowod: Int
)