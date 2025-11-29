package com.example.foodorderapplication.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapplication.CartData
import com.example.foodorderapplication.PayOutActivity
import com.example.foodorderapplication.adapter.CartAdapter
import com.example.foodorderapplication.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val isEmpty = CartData.foodNames.isEmpty()

        if (isEmpty) {
            showEmptyState()
        } else {
            showNonEmptyState()

            val adapter = CartAdapter(
                CartData.foodNames,
                CartData.prices,
                CartData.images,
                CartData.quantities
            ) { size ->
                // 👇 this runs whenever adapter removes an item
                if (size == 0) {
                    showEmptyState()
                } else {
                    showNonEmptyState()
                }
            }

            binding.CardrecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.CardrecyclerView.adapter = adapter

            binding.buttonProceed.setOnClickListener {
                val totalAmount = calculateTotalAmount()
                val intent = Intent(requireContext(), PayOutActivity::class.java)
                intent.putExtra("TOTAL_AMOUNT", totalAmount)
                startActivity(intent)
            }
        }

        return binding.root
    }

    private fun showEmptyState() {
        binding.emptyCartAnimation.visibility = View.VISIBLE
        //binding.emptyCartMessage.visibility = View.VISIBLE
        binding.CardrecyclerView.visibility = View.GONE
        binding.buttonProceed.visibility = View.GONE
    }

    private fun showNonEmptyState() {
        binding.emptyCartAnimation.visibility = View.GONE
        //binding.emptyCartMessage.visibility = View.GONE
        binding.CardrecyclerView.visibility = View.VISIBLE
        binding.buttonProceed.visibility = View.VISIBLE
    }

    private fun calculateTotalAmount(): Double {
        var total = 0.0
        for (i in CartData.foodNames.indices) {
            val priceString = CartData.prices[i].replace("$", "").trim()
            val price = priceString.toDoubleOrNull() ?: 0.0
            val quantity = CartData.quantities.getOrNull(i) ?: 1
            total += price * quantity
        }
        return total
    }
}


