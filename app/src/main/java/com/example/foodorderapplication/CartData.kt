package com.example.foodorderapplication

object CartData {
    val foodNames = mutableListOf<String>()
    val prices = mutableListOf<String>()
    val images = mutableListOf<Int>()
    val quantities = mutableListOf<Int>()

    fun addItem(name: String, price: String, image: Int) {
        val index = foodNames.indexOf(name)

        if (index != -1) {
            // ✅ Item already in cart → just increase quantity
            quantities[index] = quantities[index] + 1
        } else {
            // ✅ New item → add new row with quantity 1
            foodNames.add(name)
            prices.add(price)
            images.add(image)
            quantities.add(1)
        }
    }
}
