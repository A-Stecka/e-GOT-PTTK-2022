package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "OdcinkiOficjalne", foreignKeys = [
        ForeignKey(
            entity = OfficialPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktPoczatkowy"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = OfficialPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktKoncowy"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = OfficialPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktPosredni"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = MountainRange::class,
            parentColumns = ["id"],
            childColumns = ["FKpasmoGorskie"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class MountainPassOfficial(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var nazwa: String,
    var punkty: Int,
    var FKpunktPoczatkowy: Int,
    var FKpunktKoncowy: Int,
    var FKpunktPosredni: Int?,
    var FKpasmoGorskie: Int,
    var status: String
)
