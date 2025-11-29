package com.example.foodorderapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderapplication.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("MenuItemName")
        val foodImage = intent.getIntExtra("MenuItemImage", 0)

        binding.detailfoodname.text = foodName
        binding.detailfoodimage.setImageResource(foodImage)

        //back button click
        binding.imageButton2.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
