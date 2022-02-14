package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "PunktyOficjalne", foreignKeys = [
        ForeignKey(
            entity = MountainRange::class,
            parentColumns = ["id"],
            childColumns = ["FKpasmoGorskie"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class OfficialPoint(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var nazwa: String,
    var szerokoscGeo: Double,
    var dlugoscGeo: Double,
    var FKpasmoGorskie: Int
)
