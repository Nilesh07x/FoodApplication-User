package com.example.foodorderapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Navigation Setup
        val navController = findNavController(R.id.fragmentContainerView8)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // 🔔 Notification Icon Click
        val notificationIcon = findViewById<ImageView>(R.id.imageView5)
        notificationIcon.setOnClickListener {
            val bottomSheet = Notification_bottom_fragment()
            bottomSheet.show(supportFragmentManager, "notification_sheet")
        }
    }
}
