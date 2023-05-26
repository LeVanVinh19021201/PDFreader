package com.example.pdfreader.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.R
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.LayoutItemHomeBinding
import com.example.pdfreader.view.callback.ICallbackAllFile
import com.example.pdfreader.view.callback.ICallbackFavourite


class FavouriteAdapter(
    private val listData: ArrayList<DataFile>,
    private val callback: ICallbackFavourite,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavouriteViewHolder(
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
        if (holder is FavouriteViewHolder) {
            holder.bind(listData[position], position)
        }
    }

    inner class FavouriteViewHolder(val binding: LayoutItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFile, position: Int) {
            binding.tvTitle.text = data.id.toString()

            binding.ivFavourite.setImageResource(R.drawable.ic_favourite_reading_checked)
        }
    }
}