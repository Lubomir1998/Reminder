package com.example.reminder.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey
    var timeStamp: Long = 0L,

    var title: String = "",
    var description: String = "",
    var isHappened: Boolean = false
)