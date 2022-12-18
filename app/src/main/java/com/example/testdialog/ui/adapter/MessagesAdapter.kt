package com.example.testdialog.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testdialog.R

class MessagesAdapter(private var messages: ArrayList<String>) : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_message: TextView = itemView.findViewById(R.id.item_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MessagesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.item_message.text = messages[position]
    }

    override fun getItemCount() = messages.size

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(messages: ArrayList<String>) {
        this.messages = messages
        notifyDataSetChanged()
    }
}