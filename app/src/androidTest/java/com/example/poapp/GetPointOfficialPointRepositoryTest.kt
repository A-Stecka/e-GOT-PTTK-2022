package com.example.poapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.poapp.model.AppDatabase
import com.example.poapp.model.entity.MountainGroup
import com.example.poapp.model.entity.MountainRange
import com.example.poapp.model.entity.OfficialPoint
import com.example.poapp.model.repository.OfficialPointRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetPointOfficialPointRepositoryTest {

    private lateinit var db: AppDatabase
    private lateinit var officialPointRepository: OfficialPointRepository
    private lateinit var testPoint: OfficialPoint
    private var testPointID: Long? = null
    private var rangeID: Long? = null

    @Before
    fun createDB() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        officialPointRepository = OfficialPointRepository(db.officialPointDAO())
        val groupID = db.mountainGroupDAO().insert(MountainGroup(0, "Tatry i Podtatrze", "Polska", byteArrayOf()))
        rangeID = db.mountainRangeDAO().insert(MountainRange(0, "Tatry Wysokie", byteArrayOf(), groupID))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getNullPoint() {
        val testPointIDs = officialPointRepository.getOfficialPoint(10)
        assertTrue(testPointIDs.isEmpty())
    }

    @Test
    fun insertPoint() {
        testPoint = OfficialPoint(0, "Rusinowa Polana", 49.261037737025546, 20.08954157827796, rangeID!!.toInt())
        testPointID = officialPointRepository.insert(testPoint)
        assertNotNull(testPointID)
    }

    @Test
    fun getResult() {
        insertPoint()
        var testPointIDs = officialPointRepository.getOfficialPoint(testPointID!!.toInt())
        assertTrue(testPointIDs.isNotEmpty())
        assertTrue(testPointIDs.size == 1)
        testPointIDs = officialPointRepository.getOfficialPoint(testPoint.nazwa)
        assertTrue(testPointIDs.isNotEmpty())
        assertTrue(testPointIDs.size == 1)
    }

    @Test
    fun getCorrectResultByID() {
        insertPoint()
        val testPointIDs = officialPointRepository.getOfficialPoint(testPointID!!.toInt())
        assertEquals(testPointIDs[0].id, testPointID?.toInt())
        assertEquals(testPointIDs[0].nazwa, testPoint.nazwa)
        assertEquals(testPointIDs[0].FKpasmoGorskie, rangeID?.toInt())

    }

    @Test
    fun getCorrectResultByName() {
        insertPoint()
        val testPointIDs = officialPointRepository.getOfficialPoint(testPoint.nazwa)
        assertEquals(testPointIDs[0].id, testPointID?.toInt())
        assertEquals(testPointIDs[0].nazwa, testPoint.nazwa)
        assertEquals(testPointIDs[0].FKpasmoGorskie, rangeID?.toInt())
    }


}