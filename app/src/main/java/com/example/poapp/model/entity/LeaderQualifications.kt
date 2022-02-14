package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "UprawnieniaPrzodownikow", foreignKeys = [
        ForeignKey(
            entity = Leader::class,
            parentColumns = ["nrLegitymacji"],
            childColumns = ["FKprzodownik"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = MountainGroup::class,
            parentColumns = ["id"],
            childColumns = ["FKgrupaGorska"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class LeaderQualifications(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val FKprzodownik: Int,
    val FKgrupaGorska: Int
)