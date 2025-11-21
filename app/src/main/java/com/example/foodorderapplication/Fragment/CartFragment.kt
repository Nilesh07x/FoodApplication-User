package com.example.foodorderapplication.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapplication.PayOutActivity
import com.example.foodorderapplication.R
import com.example.foodorderapplication.adapter.CartAdapter

import com.example.foodorderapplication.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cartFoodName = listOf("Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo")
        val cartItemPrice = listOf("$5", "$6", "$8", "$9", "$10", "$10")
        val cartImages = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
        )

        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(cartItemPrice), ArrayList(cartImages))

        binding.CardrecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.CardrecyclerView.adapter = adapter
binding.buttonProceed.setOnClickListener{
    val intent = Intent(requireContext(),PayOutActivity::class.java)
    startActivity(intent)
}

        return binding.root
    }

    companion object {
        // If you plan to use factory methods later
    }
}
