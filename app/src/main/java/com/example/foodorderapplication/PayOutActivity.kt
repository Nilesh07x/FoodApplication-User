package com.example.foodorderapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderapplication.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get total amount from CartFragment
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
        val formatted = "$" + "%.2f".format(totalAmount)
        binding.payoutid.setText(formatted)

        // 🔙 Back button (orange icon)
        binding.imageButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // ✅ Place order only when all fields are filled
        binding.placemyorderbtn.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val address = binding.addressEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.nameEditText.error = "Please enter your name"
                    binding.nameEditText.requestFocus()
                }
                address.isEmpty() -> {
                    binding.addressEditText.error = "Please enter your address"
                    binding.addressEditText.requestFocus()
                }
                phone.isEmpty() -> {
                    binding.phoneEditText.error = "Please enter your phone"
                    binding.phoneEditText.requestFocus()
                }
                phone.length < 10 -> {
                    binding.phoneEditText.error = "Enter a valid phone number"
                    binding.phoneEditText.requestFocus()
                }
                else -> {
                    // ✅ SAVE CART ITEMS TO HISTORY
                    HistoryData.addFromCart()

                    // ✅ CLEAR CART AFTER ORDER
                    CartData.foodNames.clear()
                    CartData.prices.clear()
                    CartData.images.clear()
                    CartData.quantities.clear()

                    Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
