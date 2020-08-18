package com.example.reminder.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.viewmodel.MainViewModel
import com.example.reminder.R
import com.example.reminder.adapter.MyAdapter
import com.example.reminder.databinding.MainFragmentBinding
import com.example.reminder.db.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.main_fragment) {

    private val model: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private lateinit var listener: MyAdapter.OnItemClickListener


    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.title = "Reminder"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        // recyclerView itemClickListener
        listener = object : MyAdapter.OnItemClickListener {
            override fun onEventClicked(event: Event) {

                val title = event.title
                val description = event.description
                val isHappened = event.isHappened

                val action = MainScreenDirections.actionMainScreenToDetailScreen(title, description, isHappened)
                view.findNavController().navigate(action)

            }

        }


        //swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val list = model.listOfAllEventsLiveData.value!!
                model.deleteFromDB(MyAdapter(list, listener).getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.recyclerView)
        ///////////


        binding.floatingButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainScreen_to_addEvent)
        }


        // set the recyclerview
        binding.recyclerView.apply {
            adapter = MyAdapter(listOf(), listener)
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        // show all events
        model.listOfAllEventsLiveData.observe(requireActivity(), Observer {
            displayData(it)
        })


        // handle upcoming events button clicked style and observing changes from the view model
        model.getUpcomingLiveData().observe(requireActivity(), Observer { buttonUpcoming ->
            if(buttonUpcoming){
                binding.newEventsButton.setBackgroundResource(R.drawable.clicked_button)
                binding.pastEventsButton.setBackgroundResource(R.drawable.button_style)

                // show chosen events after screen rotation
                model.listOfUpcomingEventsLiveData.observe(requireActivity(), Observer {
                    displayData(it)
                })
            }
        })

        binding.newEventsButton.setOnClickListener {
            model.newButtonClick()

            model.listOfUpcomingEventsLiveData.observe(requireActivity(), Observer {
                displayData(it)
            })
        }
        ///////////////////////

        // handle past events button clicked style and observing changes from the view model
        model.getPastLiveData().observe(requireActivity(), Observer { buttonPast ->
            if(buttonPast){
                binding.pastEventsButton.setBackgroundResource(R.drawable.clicked_button)
                binding.newEventsButton.setBackgroundResource(R.drawable.button_style)

                // show chosen events after screen rotation
                model.listOfPastEventsLiveData.observe(requireActivity(), Observer {
                    displayData(it)
                })
            }
        })


        binding.pastEventsButton.setOnClickListener {
            model.pastButtonClick()

            model.listOfPastEventsLiveData.observe(requireActivity(), Observer {
                displayData(it)
            })
        }
        /////////////////////
    }

    private fun displayData(list: List<Event>){
        val adapter = binding.recyclerView.adapter as MyAdapter
        adapter.listOfEvents = list
        adapter.notifyDataSetChanged()
    }

}