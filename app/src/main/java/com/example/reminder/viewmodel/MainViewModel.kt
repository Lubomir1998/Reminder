package com.example.reminder.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    var buttonPastIsClickedLiveData = MutableLiveData<Boolean>()
    var buttonUpcomingIsClickedLiveData = MutableLiveData<Boolean>()


    init {
        buttonPastIsClickedLiveData.value = false
        buttonUpcomingIsClickedLiveData.value = false
    }


    fun insertInDB(event: Event){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(event)
        }
    }

    fun updateFromDB(event: Event){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(event)
        }
    }

    fun deleteFromDB(event: Event){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(event)
        }
    }

    fun pastButtonClick(){
        buttonUpcomingIsClickedLiveData.value = false
        buttonPastIsClickedLiveData.value = !buttonPastIsClickedLiveData.value!!
    }

    fun newButtonClick(){
        buttonPastIsClickedLiveData.value = false
        buttonUpcomingIsClickedLiveData.value = !buttonUpcomingIsClickedLiveData.value!!
    }


}