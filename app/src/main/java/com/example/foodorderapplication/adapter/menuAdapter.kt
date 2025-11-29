package com.example.foodorderapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.CartData
import com.example.foodorderapplication.DetailsActivity
import com.example.foodorderapplication.databinding.MenuItemBinding

class menuAdapter(
    private val menuItemsName: List<String>,
    private val menuItemPrice: List<String>,
    private val menuImages: List<Int>,
    private val context: Context
) : RecyclerView.Adapter<menuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val name = menuItemsName[position]
        val price = menuItemPrice[position]
        val image = menuImages[position]

        with(holder.binding) {
            menuFoodName.text = name
            menuPrice.text = price
            menuimage.setImageResource(image)

            // 🔍 Open details when clicking the whole card
            root.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("MenuItemName", name)
                intent.putExtra("MenuItemImage", image)
                context.startActivity(intent)
            }

            // 🛒 Add to Cart button
            menuAddToCart.setOnClickListener {
                CartData.addItem(name, price, image)
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = menuItemsName.size
}
