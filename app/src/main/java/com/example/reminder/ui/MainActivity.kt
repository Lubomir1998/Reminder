package com.example.reminder.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.reminder.R
import com.example.reminder.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigateToDetailScreen(intent)


        navController = Navigation.findNavController(this,
            R.id.fragment
        )
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()

        return super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToDetailScreen(intent)
    }


    private fun navigateToDetailScreen(intent: Intent?){
        if(intent?.action == "Go to detail screen"){

            val title = intent.getStringExtra("1")
            val id = intent.getLongExtra("2", 0)
            val description = intent.getStringExtra("3")
            val isHappened = true

            val action = MainScreenDirections.actionMainScreenToDetailScreen(id, title!!, description!!, isHappened)

            fragment.findNavController().navigate(action)

            // clear the action so the detail screen won't appear when we rotate the screen for example
            intent.action = ""
        }
    }

    // remind for event when the app is not running
    override fun onDestroy() {
        super.onDestroy()

        navigateToDetailScreen(intent)
    }

}