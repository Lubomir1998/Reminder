package com.example.reminder.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.databinding.RowItemBinding
import com.example.reminder.db.Event
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(var listOfEvents: List<Event>, private var itemClicklistener: OnItemClickListener): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEvent = listOfEvents[position]

        holder.eventTitle.text = currentEvent.title

        // format the timestamp to date
        // to show it in a proper format to the user
        val parser = SimpleDateFormat("yyyyMMddHHmm")
        val date = parser.parse(currentEvent.timeStamp.toString())

        val formatter = SimpleDateFormat("HH:mm, dd.MM.yyyy", Locale.getDefault())

        val newDate = formatter.format(date!!)

        holder.eventTimeStamp.text = newDate
        ////////

        if(currentEvent.isHappened){
            holder.isHappenedDot.setImageResource(R.drawable.event_past)
        }
        else{
            holder.isHappenedDot.setImageResource(R.drawable.event_upcomming)
        }

        holder.click(currentEvent, itemClicklistener)

    }

    fun getNoteAt(position: Int): Event{
        return listOfEvents[position]
    }


    override fun getItemCount(): Int = listOfEvents.size

    class MyViewHolder(itemView: RowItemBinding): RecyclerView.ViewHolder(itemView.root){

        val eventTitle = itemView.eventTitleTextView
        val eventTimeStamp = itemView.timestampTextView
        val isHappenedDot = itemView.imageView

        fun click(event: Event, listener: OnItemClickListener){
            itemView.setOnClickListener{
                listener.onEventClicked(event)
            }
        }

    }

    interface OnItemClickListener{
        fun onEventClicked(event: Event)
    }

}