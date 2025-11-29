package com.example.foodorderapplication.adapter

import android.widget.Toast
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.CartData
import com.example.foodorderapplication.databinding.BuyAgainItemBinding

class BuyAgainAdapter(
    private val buyAgainFoodName: ArrayList<String>,
    private val buyAgainFoodPrice: ArrayList<String>,
    private val buyAgainFoodImage: ArrayList<Int>
) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {


    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {

        val name = buyAgainFoodName[position]
        val price = buyAgainFoodPrice[position]
        val image = buyAgainFoodImage[position]

        holder.bind(name, price, image)

        // ⭐ BUY AGAIN BUTTON FUNCTIONALITY
        holder.binding.BuyAgainButton.setOnClickListener {
            CartData.addItem(name, price, image)

            Toast.makeText(
                holder.itemView.context,
                "Added to cart",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    class BuyAgainViewHolder(val binding: BuyAgainItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(foodName: String, foodPrice: String, foodImage: Int) {

            binding.BuyAgainFoodName.text = foodName
            binding.BuyAgainFoodPrice.text = foodPrice
            binding.BuyAgainFoodImage.setImageResource(foodImage)
        }
    }
}
