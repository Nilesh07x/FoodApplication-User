package com.example.foodorderapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.databinding.MenuItemBinding

class menuAdapter(private val menuItemsName: List<String>, private val menuItemPrice: List<String>, private val MenuImage: List<Int>):RecyclerView.Adapter<menuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuItemsName.size
    inner class MenuViewHolder(private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text=menuItemsName[position]
                menuPrice.text=menuItemPrice[position]
                menuimage.setImageResource((MenuImage[position]))
            }
        }


    }
}