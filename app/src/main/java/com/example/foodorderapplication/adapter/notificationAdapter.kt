package com.example.foodorderapplication.adapter

import android.app.Notification
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapplication.databinding.FragmentNotificationBottomFragmentBinding
import com.example.foodorderapplication.databinding.NotificationItemBinding
import java.net.BindException

class notificationAdapter(private var notification:ArrayList<String>, private var notificationImage:ArrayList<Int>): RecyclerView.Adapter<notificationAdapter.NotificationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notificationAdapter.NotificationViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }




    override fun onBindViewHolder(
        holder: notificationAdapter.NotificationViewHolder,
        position: Int
    ) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = notification.size
    inner class NotificationViewHolder(private val binding: NotificationItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                notificationtextview.text=notification[position]
                notificationImageview.setImageResource(notificationImage[position])
            }
        }

    }
}