package com.example.foodorderapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.CartData
import com.example.foodorderapplication.DetailsActivity
import com.example.foodorderapplication.databinding.PopularItemBinding

class popularAdapter(
    private val items: List<String>,
    private val price: List<String>,
    private val image: List<Int>,
    private val requireContext: Context
) : RecyclerView.Adapter<PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            PopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val img = image[position]
        val itemPrice = price[position]

        holder.bind(item, itemPrice, img)

        // Open details when clicking the whole card
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", img)
            requireContext.startActivity(intent)
        }

        // ✅ Add to cart button
        holder.setAddToCartClickListener {
            CartData.addItem(item, itemPrice, img)
            Toast.makeText(requireContext, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = items.size
}

class PopularViewHolder(private val binding: PopularItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val imagesView = binding.imageView6

    fun bind(item: String, price: String, images: Int) {
        binding.foodnamepopular.text = item
        binding.pricepopular.text = price
        imagesView.setImageResource(images)
    }

    fun setAddToCartClickListener(onClick: () -> Unit) {
        binding.addtocartpopular.setOnClickListener { onClick() }
    }
}
