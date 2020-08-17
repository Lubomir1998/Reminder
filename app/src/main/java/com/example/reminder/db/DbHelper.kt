package com.example.reminder.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 3)
abstract class DbHelper: RoomDatabase() {

    abstract fun getEventDao(): EventDao

}