package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Pracownicy", foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["FKuzytkownik"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Member(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val FKuzytkownik: Int,
    var nazwaOrganizacji: String,
    var dataZatrudnienia: String
)