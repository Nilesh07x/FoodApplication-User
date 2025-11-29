package com.example.foodorderapplication

object HistoryData {
    val names = mutableListOf<String>()
    val prices = mutableListOf<String>()
    val images = mutableListOf<Int>()

    // Copy current cart items into history
    fun addFromCart() {
        for (i in CartData.foodNames.indices) {
            names.add(CartData.foodNames[i])
            prices.add(CartData.prices[i])
            images.add(CartData.images[i])
        }
    }

    fun clear() {
        names.clear()
        prices.clear()
        images.clear()
    }
}
