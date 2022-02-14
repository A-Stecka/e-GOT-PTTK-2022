package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Odznaki", foreignKeys = [
        ForeignKey(
            entity = Tourist::class,
            parentColumns = ["nrKsiazeczki"],
            childColumns = ["FKturysta"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Badge(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val FKturysta: Int,
    var wymaganePunkty: Int,
    var dataPrzyznania: String,
    var rodzaj: String,
    var stopien: String
)