package com.example.reminder.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.reminder.viewmodel.MainViewModel
import com.example.reminder.R
import com.example.reminder.databinding.AddEventFragmentBinding
import com.example.reminder.dateandtime.DatePickerFragment
import com.example.reminder.dateandtime.TimePickerFragment
import com.example.reminder.db.Event
import com.example.reminder.notify.AlertReceiver
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddEvent : Fragment(R.layout.add_event_fragment), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private val model: MainViewModel by viewModels()
    private lateinit var binding: AddEventFragmentBinding
    private var calendar = Calendar.getInstance()

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.title = "Add event"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEventFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dateButton.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.setTargetFragment(this, 0)
            fragmentManager?.let { it1 -> datePicker.show(it1, "Date") }
        }

        binding.clockButton.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.setTargetFragment(this, 0)
            fragmentManager?.let { it1 -> timePicker.show(it1, "Time") }
        }

        binding.setButton.setOnClickListener {
            addEvent()
        }



    }

    private fun addEvent(){
        if(binding.addEventEditText.text.toString() == "" || binding.dateTextView.text.toString() == "YYYY-MM-DD" || binding.timeTextView.text.toString() == "HH:MM"){
            Toast.makeText(requireContext(), "Title, date and time must be selected!", Toast.LENGTH_SHORT).show()
        }
        else{
            val dateStamp = binding.dateTextView.text.toString().replace("-", "")
            val clockStamp = binding.timeTextView.text.toString().replace(":", "")
            val timestamp = "$dateStamp$clockStamp".toLong()

            val title = binding.addEventEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            val event = Event(timestamp, title, description,false)

            model.insertInDB(event)

            binding.addEventEditText.text.clear()

            startAlarm(title)

            Toast.makeText(requireContext(), "Event added", Toast.LENGTH_SHORT).show()
            view?.findNavController()?.navigate(R.id.action_addEvent_to_mainScreen)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val df = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

        binding.dateTextView.text = df

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        var h = hourOfDay.toString()
        var m = minute.toString()

        if(hourOfDay < 10){
            h = "0$hourOfDay"
        }

        if(minute < 10){
            m = "0$minute"
        }

        binding.timeTextView.text = "$h:$m"
    }

    private fun startAlarm(title: String){

        val alarmManager: AlarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), AlertReceiver::class.java)

        intent.putExtra("key", title)

        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 1, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }

}