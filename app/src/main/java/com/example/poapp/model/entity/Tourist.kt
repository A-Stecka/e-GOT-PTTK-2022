package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.RESTRICT
import androidx.room.PrimaryKey

@Entity(
    tableName = "Turysci", foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["FKuzytkownik"],
            onDelete = RESTRICT
        )
    ]
)
data class Tourist(
    @PrimaryKey(autoGenerate = true) val nrKsiazeczki: Int,
    val FKuzytkownik: Int,
    var sumaPunktow: Int,
    var czyNiepelnosprawny: Boolean
)