package com.example.reminder.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM Event ORDER BY timestamp ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM Event WHERE isHappened = 1 ORDER BY timestamp ASC")
    fun getPastEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM Event WHERE isHappened = 0 ORDER BY timestamp ASC")
    fun getUpcomingEvents(): LiveData<List<Event>>

}