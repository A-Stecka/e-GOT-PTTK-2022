package com.example.poapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GrupyGorskie")
data class MountainGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nazwa: String,
    var kraj: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var obszar: ByteArray?
)
