package com.example.foodorderapplication

import android.app.Notification
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorderapplication.adapter.notificationAdapter
import com.example.foodorderapplication.databinding.FragmentNotificationBottomFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

class Notification_bottom_fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

// Inflate the layout for this fragment
        binding = FragmentNotificationBottomFragmentBinding.inflate (layoutInflater, container, false)
        val notification = listOf("Your Order has been Cancelled", "Order has been taken by the driver","Congrats your Order is placed ")
        val notificationImages=listOf(R.drawable.sademoji,R.drawable.icon__1_,R.drawable.group_805)
        val adapter= notificationAdapter(
            ArrayList(notification), ArrayList(notificationImages)
        )
        return binding.root
    }
    companion object {

    }
}