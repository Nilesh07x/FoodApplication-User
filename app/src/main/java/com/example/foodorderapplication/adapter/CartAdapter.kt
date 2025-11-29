package com.example.foodorderapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.databinding.CardItemBinding

class CartAdapter(
    private val cartItems: MutableList<String>,
    private val cartItemPrice: MutableList<String>,
    private val cartImage: MutableList<Int>,
    private val itemQuantities: MutableList<Int>,
    private val onCartChanged: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    inner class CartViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.cardFoodname.text = cartItems[position]
            binding.cardItemprice.text = cartItemPrice[position]
            binding.cardImage.setImageResource(cartImage[position])
            binding.cardItemQuantity.text = itemQuantities[position].toString()

            binding.plusbutton.setOnClickListener {
                itemQuantities[position]++
                binding.cardItemQuantity.text = itemQuantities[position].toString()
            }

            binding.minusbutton.setOnClickListener {
                if (itemQuantities[position] > 1) {
                    itemQuantities[position]--
                    binding.cardItemQuantity.text = itemQuantities[position].toString()
                }
            }

            binding.deletebutton.setOnClickListener {
                removeItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size

    private fun removeItem(position: Int) {
        cartItems.removeAt(position)
        cartItemPrice.removeAt(position)
        cartImage.removeAt(position)
        itemQuantities.removeAt(position)

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cartItems.size)

        onCartChanged(cartItems.size)
    }

}
