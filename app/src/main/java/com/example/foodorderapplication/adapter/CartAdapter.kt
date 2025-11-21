package com.example.foodorderapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RemoteViews.RemoteCollectionItems
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.databinding.CardItemBinding

class CartAdapter(private val cartItems: MutableList<String>,private val cartItemPrice:MutableList<String>,private var cartImage:MutableList<Int>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

private val itemQuantities=IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size;
   inner  class CartViewHolder(private val binding:CardItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply{
                val quantity = itemQuantities[position]
                cardFoodname.text=cartItems[position]
                cardItemprice.text= cartItemPrice[position]
                cardImage.setImageResource(cartImage[position])
                cardItemQuantity.text= quantity.toString()

                minusbutton.setOnClickListener{
                    deceaseQuantity(position)
                }
                plusbutton.setOnClickListener{
                    increaseQuantity(position)
                }
                deletebutton.setOnClickListener{
                    val itemPosition = adapterPosition
                    if(itemPosition!= RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)
                    }

                }

            }
        }
       private fun increaseQuantity(position: Int){
           if (itemQuantities [position]<10){
               itemQuantities [position]++
               binding.cardItemQuantity.text = itemQuantities[position].toString()
           }
       }
       private fun deceaseQuantity(position: Int) {
           if (itemQuantities[position] > 1) {
               itemQuantities[position]--
               binding.cardItemQuantity.text = itemQuantities[position].toString()
           }
       }

       private fun deleteItem(position: Int) {
           cartItems.removeAt(position)
           cartImage.removeAt(position)
           cartItemPrice.removeAt(position)
           notifyItemRemoved(position)
           notifyItemRangeChanged(position, cartItems.size)
       }
   }


}