package com.example.reminder.repository

import com.example.reminder.db.Event
import com.example.reminder.db.EventDao
import javax.inject.Inject

class Repository
@Inject constructor(private val eventDao: EventDao){

    suspend fun insert(event: Event){
        eventDao.insert(event)
    }

    suspend fun update(event: Event){
        eventDao.update(event)
    }

    suspend fun delete(event: Event){
        eventDao.delete(event)
    }

    fun getAllEvents() = eventDao.getAllEvents()

    fun getUpcomingEvents() = eventDao.getUpcomingEvents()

    fun getPastEvents() = eventDao.getPastEvents()

}