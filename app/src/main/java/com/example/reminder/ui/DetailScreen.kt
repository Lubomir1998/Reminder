package com.example.reminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.reminder.R
import com.example.reminder.databinding.DetailScreenFragmentBinding

class DetailScreen: Fragment(R.layout.detail_screen_fragment) {

    private lateinit var binding: DetailScreenFragmentBinding
    private val args: DetailScreenArgs by navArgs()

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.title = "Event info"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleTextView.text = args.eventTitle
        binding.descriptionTextView.text = args.eventDescription

        if(args.eventIsHappened){
            binding.isHapennedImg.setImageResource(R.drawable.event_past)
        }
        else{
            binding.isHapennedImg.setImageResource(R.drawable.event_upcomming)
        }

    }


}