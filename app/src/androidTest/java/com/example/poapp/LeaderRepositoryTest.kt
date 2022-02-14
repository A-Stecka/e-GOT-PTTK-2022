package com.example.poapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.poapp.model.AppDatabase
import com.example.poapp.model.dao.LeaderDAO
import com.example.poapp.model.dao.TouristDAO
import com.example.poapp.model.dao.UserDAO
import com.example.poapp.model.entity.Leader
import com.example.poapp.model.entity.Tourist
import com.example.poapp.model.entity.User
import com.example.poapp.model.repository.LeaderRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LeaderRepositoryTest {
    private lateinit var leaderDAO: LeaderDAO
    private lateinit var userDAO: UserDAO
    private lateinit var touristDAO: TouristDAO
    private lateinit var leaderRepository: LeaderRepository
    private lateinit var db: AppDatabase
    private var leaderID: Int? = null
    private var userID: Int? = null

    @Before
    fun createDB() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        leaderDAO = db.leaderDAO()
        userDAO = db.userDAO()
        touristDAO = db.touristDAO()
        leaderRepository = LeaderRepository(leaderDAO)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getLeaderNull() {
        val leaderNull = leaderRepository.getLeader(10)
        assertNull(leaderNull)
    }

    @Test
    fun insertLeader() {
        userID = userDAO.insert(User(0, "jkowalski", "p@ssw0rd", "jkowalski@gmail.com", "Jan", "Kowalski", "27.02.1995", 1)).toInt()
        val touristID = touristDAO.insert(Tourist(0, userID!!.toInt(), 0, false))
        leaderID = leaderDAO.insert(Leader(0, touristID.toInt())).toInt()
        assertNotNull(leaderID)
    }

    @Test
    fun findLeader() {
        insertLeader()
        val found = leaderID?.let { leaderRepository.getLeader(it.toLong()) }
        assertEquals(found?.nrLegitymacji, leaderID)
    }

    @Test
    fun findLeaderTourist() {
        insertLeader()
        val found = leaderID?.let { leaderRepository.getLeaderUser(it.toLong()) }
        assertEquals(found?.id, userID)
        assertEquals(found?.imie, "Jan")
        assertEquals(found?.nazwisko, "Kowalski")
        assertEquals(found?.rola, 1)
    }

}