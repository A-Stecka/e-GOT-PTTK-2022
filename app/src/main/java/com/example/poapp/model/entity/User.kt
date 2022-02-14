package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Uzytkownicy")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var login: String,
    var haslo: String,
    var email: String,
    var imie: String,
    var nazwisko: String,
    var dataUrodzenia: String,
    var rola: Int
)