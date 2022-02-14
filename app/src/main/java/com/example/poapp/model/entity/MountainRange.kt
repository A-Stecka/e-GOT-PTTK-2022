package com.example.poapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "PasmaGorskie", foreignKeys = [
        ForeignKey(
            entity = MountainGroup::class,
            parentColumns = ["id"],
            childColumns = ["FKgrupaGorska"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MountainRange(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var nazwa: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var obszar: ByteArray?,
    val FKgrupaGorska: Long
)
