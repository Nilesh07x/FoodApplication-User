package com.example.foodorderapplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapplication.HistoryData
import com.example.foodorderapplication.R
import com.example.foodorderapplication.adapter.BuyAgainAdapter
import com.example.foodorderapplication.databinding.FragmentHistoryBinding


class HistoryFragment: Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        // Take items from HistoryData
        val buyAgainFoodName = ArrayList(HistoryData.names)
        val buyAgainFoodPrice = ArrayList(HistoryData.prices)
        val buyAgainFoodImage = ArrayList(HistoryData.images)

        buyAgainAdapter = BuyAgainAdapter(
            buyAgainFoodName,
            buyAgainFoodPrice,
            buyAgainFoodImage
        )

        binding.BuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.BuyAgainRecyclerView.adapter = buyAgainAdapter
    }
}
