package com.example.reminder.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.db.Event
import com.example.reminder.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel
@ViewModelInject constructor(private val repository: Repository): ViewModel() {

    val listOfAllEventsLiveData: LiveData<List<Event>> = repository.getAllEvents()
    val listOfUpcomingEventsLiveData: LiveData<List<Event>> = repository.getUpcomingEvents()
    val listOfPastEventsLiveData: LiveData<List<Event>> = repository.getPastEvents()

    fun insertInDB(event: Event){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(event)
        }
    }

    fun deleteFromDB(event: Event){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(event)
        }
    }

}