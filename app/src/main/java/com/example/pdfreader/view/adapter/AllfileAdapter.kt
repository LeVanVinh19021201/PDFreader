package com.example.pdfreader.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.LayoutItemHomeBinding
import com.example.pdfreader.view.callback.ICallbackAllFile
import com.example.pdfreader.view.callback.TagAllFile

class AllfileAdapter(
    private val listData: ArrayList<DataFile>,
    private val callback: ICallbackAllFile,
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

    inner class AllfileViewHolder(val binding: LayoutItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFile, position: Int) {
            binding.tvTitle.text = data.id.toString()

            if(data.isFavourite==1){
                binding.tvTitle.text = "Vinhh"
            }
            binding.ivFavourite.setOnClickListener {
                callback.callbackALlFile(TagAllFile.ON_CLICK_FAVOURITE,data)
            }

            binding.root.setOnClickListener {
                callback.callbackALlFile(TagAllFile.ON_CLICK_OPEN_FILE,data)
            }
        }
    }
}