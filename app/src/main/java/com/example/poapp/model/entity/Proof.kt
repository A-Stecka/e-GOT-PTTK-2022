package com.example.poapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Dowody", foreignKeys = [
        ForeignKey(
            entity = Leader::class,
            parentColumns = ["nrLegitymacji"],
            childColumns = ["FKprzodownik"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Proof(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var zdjecie: ByteArray?,
    val FKprzodownik: Int?
)