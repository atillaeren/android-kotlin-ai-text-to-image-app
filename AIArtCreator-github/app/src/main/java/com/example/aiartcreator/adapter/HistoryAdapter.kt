package com.example.aiartcreator.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aiartcreator.daoModel.RoomEntity
import com.example.aiartcreator.databinding.HistoryImageItemBinding

class HistoryAdapter(private var data: List<RoomEntity>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(val binding : HistoryImageItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.description.text = data[position].description

        val byteArray = data[position].image
        val bitmap = byteArray.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }
        holder.binding.image.setImageBitmap(bitmap)
    }
}