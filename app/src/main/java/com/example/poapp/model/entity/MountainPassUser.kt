package com.example.poapp.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlin.math.roundToInt

@Entity(
    tableName = "OdcinkiWlasne", foreignKeys = [
        ForeignKey(
            entity = Tourist::class,
            parentColumns = ["nrKsiazeczki"],
            childColumns = ["FKturysta"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = UserPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktPoczatkowyWlasny"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = UserPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktKoncowyWlasny"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = UserPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktPosredniWlasny"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = OfficialPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktPoczatkowyOficjalny"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = OfficialPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktKoncowyOficjalny"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = OfficialPoint::class,
            parentColumns = ["id"],
            childColumns = ["FKpunktPosredniOficjalny"],
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
data class MountainPassUser(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val FKturysta: Int,
    var nazwa: String,
    var dlugosc: Double,
    var podejscie: Int,
    val FKpunktPoczatkowyWlasny: Int?,
    val FKpunktKoncowyWlasny: Int?,
    val FKpunktPosredniWlasny: Int?,
    val FKpunktPoczatkowyOficjalny: Int?,
    val FKpunktKoncowyOficjalny: Int?,
    val FKpunktPosredniOficjalny: Int?,
    val FKpasmoGorskie: Int
) {
    var punkty: Int = (
            if (podejscie < 51)
                dlugosc.roundToInt()
            else {
                if (podejscie < 100)
                    1 + dlugosc.roundToInt()
                else
                    podejscie / 100 + dlugosc.roundToInt()
            })
}
