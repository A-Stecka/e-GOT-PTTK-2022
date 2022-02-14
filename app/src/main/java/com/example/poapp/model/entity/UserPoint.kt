package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "PunktyWlasne", foreignKeys = [
        ForeignKey(
            entity = Tourist::class,
            parentColumns = ["nrKsiazeczki"],
            childColumns = ["FKturysta"],
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
data class UserPoint(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val FKturysta: Int,
    var nazwa: String,
    var szerokoscGeo: Double,
    var dlugoscGeo: Double,
    val FKpasmoGorskie: Int
)