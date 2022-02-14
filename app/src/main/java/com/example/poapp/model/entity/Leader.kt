package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Przodownicy", foreignKeys = [
        ForeignKey(
            entity = Tourist::class,
            parentColumns = ["nrKsiazeczki"],
            childColumns = ["FKturysta"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Leader(
    @PrimaryKey(autoGenerate = true) val nrLegitymacji: Int,
    val FKturysta: Int
)