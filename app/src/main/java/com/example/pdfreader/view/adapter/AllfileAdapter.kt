package com.example.pdfreader.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.LayoutItemHomeBinding

class AllfileAdapter(
    private val listData: ArrayList<DataFile>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllfileViewHolder(
            LayoutItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AllfileViewHolder) {
            holder.bind(listData[position], position)
        }
    }

    inner class AllfileViewHolder(binding: LayoutItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFile, position: Int) {

        }
    }
}