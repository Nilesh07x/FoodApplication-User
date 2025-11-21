package com.example.foodorderapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        binding.loginbttn.setOnClickListener{
//            val intent= Intent(this,SigninActivity::class.java)
//            startActivity(intent)
//        }

        binding.dnthavebttn.setOnClickListener{
            val intent= Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }
    }
}
