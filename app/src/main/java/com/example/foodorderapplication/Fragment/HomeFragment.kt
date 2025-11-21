package com.example.foodorderapplication.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodorderapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.adapter.PopularViewHolder
import com.example.foodorderapplication.adapter.popularAdapter
import com.example.foodorderapplication.databinding.FragmentHomeBinding
import com.example.foodorderapplication.databinding.FragmentMenuBottomSheetBinding
import com.example.foodorderapplication.menuBottomSheetFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewAllMenu.setOnClickListener{
            val bottomSheetDialog= menuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
            add(SlideModel(R.drawable.foodbanner3, ScaleTypes.FIT))
            add(SlideModel(R.drawable.foodbanner2, ScaleTypes.FIT))
        }

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                // Avoid using TODO without a handler. Just log or leave empty
                Toast.makeText(requireContext(), "Double clicked: $position", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(position: Int) {
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
        val foodName = listOf("Burger","Sandwich","momo","items")
        val price = listOf("$5","$7","$8","$10")
        val popularFoodImages = listOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4)
        val adapter = popularAdapter(foodName,price,popularFoodImages)
        binding.popularRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter=adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
