package com.example.foodorderapplication.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodorderapplication.R
import com.example.foodorderapplication.adapter.popularAdapter
import com.example.foodorderapplication.databinding.FragmentHomeBinding
import com.example.foodorderapplication.menuBottomSheetFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // When clicking View Menu
        binding.viewAllMenu.setOnClickListener {
            // ✔ Show menu list + title
            binding.popularRecyclerView.visibility = View.VISIBLE
            binding.textView22.visibility = View.VISIBLE

            // ❗ Hide hint text once menu is opened
            binding.homeHintText.visibility = View.GONE

            // ✔ Open bottom sheet
            val bottomSheetDialog = menuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ---------------------------------------------------------
        // ✔ HIDE MENU INITIALLY, SHOW HINT
        // ---------------------------------------------------------
        binding.popularRecyclerView.visibility = View.GONE
        binding.textView22.visibility = View.GONE
        binding.homeHintText.visibility = View.VISIBLE
        // ---------------------------------------------------------

        // IMAGE SLIDER
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
            add(SlideModel(R.drawable.foodbanner3, ScaleTypes.FIT))
            add(SlideModel(R.drawable.foodbanner2, ScaleTypes.FIT))
        }

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.imageSlider.startSliding(2000)

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                Toast.makeText(
                    requireContext(),
                    "Double clicked: $position",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemSelected(position: Int) {
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })

        // POPULAR MENU
        val foodName = listOf("Burger", "Soup", "Pasta", "Rolls","Salad","Fruits")
        val price = listOf("$5", "$7", "$8", "$10","$9","$12")
        val popularFoodImages = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu6,
            R.drawable.menu7,
        )

        val adapter = popularAdapter(foodName, price, popularFoodImages, requireContext())
        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        binding.imageSlider.stopSliding()
        _binding = null
        super.onDestroyView()
    }
}
