package com.example.poapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.model.repository.OfficialPointRepository
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.YearMonth

class Utils {

    companion object {

        fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
            val nh = (bitmap.height * (2048.0 / bitmap.width)).toInt()
            val scaled = Bitmap.createScaledBitmap(bitmap, 2048, nh, true)
            val outputStream = ByteArrayOutputStream()
            scaled.compress(Bitmap.CompressFormat.JPEG, 0, outputStream)
            return outputStream.toByteArray()
        }

        fun getImage(byteArray: ByteArray?): Bitmap? {
            return if (byteArray != null) {
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } else null
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
            if (month < 0 || year < 1999 || month > 11 || dayOfMonth < 0 || LocalDate.now().year < year || dayOfMonth > YearMonth.of(year, month + 1).lengthOfMonth()) {
                throw IllegalArgumentException()
            }
            var monthString = (month + 1).toString()
            if (month + 1 < 10) {
                monthString = "0${month + 1}"
            }
            var dayString = dayOfMonth.toString()
            if (dayOfMonth < 10) {
                dayString = "0$dayOfMonth"
            }
            return "$year-$monthString-$dayString"
        }

        fun pointsNotInSameRange(mountainPassOfficial: MountainPassOfficial, repository: OfficialPointRepository): Boolean {
            val mountainRangeStart = repository.getOfficialPoint(mountainPassOfficial.FKpunktPoczatkowy)[0].FKpasmoGorskie
            val mountainRangeEnd = repository.getOfficialPoint(mountainPassOfficial.FKpunktKoncowy)[0].FKpasmoGorskie
            var mountainRangeThrough = mountainRangeStart
            if (mountainPassOfficial.FKpunktPosredni != null && mountainPassOfficial.FKpunktPosredni != 0)
                mountainRangeThrough = repository.getOfficialPoint(mountainPassOfficial.FKpunktPosredni!!)[0].FKpasmoGorskie
            return (mountainRangeStart != mountainRangeEnd || mountainRangeStart != mountainRangeThrough || mountainRangeEnd != mountainRangeThrough)
        }
    }
}