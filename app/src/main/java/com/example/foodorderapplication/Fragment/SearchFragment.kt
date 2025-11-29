package com.example.foodorderapplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderapplication.R
import com.example.foodorderapplication.adapter.menuAdapter
import com.example.foodorderapplication.databinding.FragmentSearchBinding



class SearchFragment :Fragment () {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: menuAdapter
    private val originalMenuFoodName =
        listOf("Burger", "Soup", "Pasta", "Rolls", "Sandwich", "Salad")
    private val OriginalmenuItemPrice = listOf("$5", "$6", "$8", "$9", "$10", "$9")
    private val OriginalmenuImages = listOf(
        R.drawable.menu1,
        R.drawable.menu2,
        R.drawable.menu3,
        R.drawable.menu4,
        R.drawable.menu5,
        R.drawable.menu6,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private val filteredMenuFoodName= mutableListOf<String>()
    private val filteredMenuItemPrice= mutableListOf<String>()
    private val filteredMenuImage= mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = menuAdapter(filteredMenuFoodName, filteredMenuItemPrice, filteredMenuImage,requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        setupSearchView()
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filteredMenuFoodName.clear()
        filteredMenuItemPrice.clear()
        filteredMenuImage.clear()

        filteredMenuFoodName.addAll(originalMenuFoodName)
        filteredMenuItemPrice.addAll(OriginalmenuItemPrice)
        filteredMenuImage.addAll(OriginalmenuImages)
        adapter.notifyDataSetChanged()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })

    }
        private fun filterMenuItems(query: String) {
            filteredMenuFoodName.clear()
            filteredMenuItemPrice.clear()
            filteredMenuImage.clear()

            originalMenuFoodName.forEachIndexed{index, foodName->
                if(foodName.contains(query, ignoreCase = true)){
                    filteredMenuFoodName.add(foodName)
                    filteredMenuItemPrice.add(OriginalmenuItemPrice[index])
                    filteredMenuImage.add(OriginalmenuImages[index])
                }
            }
            adapter.notifyDataSetChanged()
        }

   companion object{
    }
}
