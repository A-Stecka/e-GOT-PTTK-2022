package com.example.poapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.poapp.model.AppDatabase
import com.example.poapp.model.entity.MountainGroup
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.model.entity.MountainRange
import com.example.poapp.model.entity.OfficialPoint
import com.example.poapp.model.repository.OfficialPointRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PointsInSameRangeTest {
    private var range1: Int? = null
    private var range2: Int? = null
    private var group: Int? = null
    private var group2: Int? = null
    private var point11: Int? = null
    private var point12: Int? = null
    private var point2: Int? = null

    private lateinit var db: AppDatabase
    private lateinit var repository: OfficialPointRepository

    @DelicateCoroutinesApi
    @Before
    fun getTestPoints() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        repository = OfficialPointRepository(db.officialPointDAO())
        group = db.mountainGroupDAO().insert(MountainGroup(0, "group", "", byteArrayOf())).toInt()
        group2 = db.mountainGroupDAO().insert(MountainGroup(0, "group2", "", byteArrayOf())).toInt()
        range1 = db.mountainRangeDAO().insert(MountainRange(0, "1", byteArrayOf(), group!!.toLong())).toInt()
        range2 = db.mountainRangeDAO().insert(MountainRange(0, "1", byteArrayOf(), group2!!.toLong())).toInt()
        point11 = db.officialPointDAO().insert(OfficialPoint(0, "1", 0.0, 0.0, range1!!)).toInt()
        point12 = db.officialPointDAO().insert(OfficialPoint(0, "2", 0.0, 0.0, range1!!)).toInt()
        point2 = db.officialPointDAO().insert(OfficialPoint(0, "3", 0.0, 0.0, range2!!)).toInt()
    }

    @After
    @Throws(IOException::class)
    fun deleteTestData() {
        db.close()
    }

    @Test
    fun startEndCorrect() {
        val result = Utils.pointsNotInSameRange(
            MountainPassOfficial(0, "", 1, point11!!, point12!!, null, 0, ""),
            repository
        )
        assertFalse(result)
    }

    @Test
    fun startEndIncorrect() {
        val result = Utils.pointsNotInSameRange(
            MountainPassOfficial(0, "", 1, point11!!, point2!!, null, 0, ""),
            repository
        )
        assertTrue(result)
    }

    @Test
    fun endStartIncorrect() {
        val result = Utils.pointsNotInSameRange(
            MountainPassOfficial(0, "", 1, point2!!, point12!!, null, 0, ""),
            repository
        )
        assertTrue(result)
    }

    @Test
    fun startEndCorrectThroughIncorrect() {
        val result = Utils.pointsNotInSameRange(
            MountainPassOfficial(0, "", 1, point11!!, point12!!, point2!!, 0, ""),
            repository
        )
        assertTrue(result)
    }

    @Test
    fun startEndCorrectThroughCorrect() {
        val result = Utils.pointsNotInSameRange(
            MountainPassOfficial(0, "", 1, point11!!, point12!!, point12!!, 0, ""),
            repository
        )
        assertFalse(result)
    }

    @Test
    fun startThroughCorrectEndIncorrect() {
        val result = Utils.pointsNotInSameRange(
            MountainPassOfficial(0, "", 1, point11!!, point2!!, point12!!, 0, ""),
            repository
        )
        assertTrue(result)
    }
}