package com.example.pdfreader.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.R
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.LayoutItemPdfBinding
import com.example.pdfreader.utils.Const
import com.example.pdfreader.view.callback.ICallbackRecent
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class RecentAdapter(
    private val listData: ArrayList<DataFile>,
    private val callback: ICallbackRecent
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecentViewHolder(
            LayoutItemPdfBinding.inflate(
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
        if (holder is RecentViewHolder) {
            holder.bind(listData[position], position)
        }
    }

    inner class RecentViewHolder(val binding: LayoutItemPdfBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFile, position: Int) {

            binding.tvNameFile.text = File(data.path).name
            val formatter = SimpleDateFormat(Const.PATTERN_FORMAT_TIME_HOME, Locale.getDefault())
            val dateString = formatter.format(File(data.path).lastModified())
            binding.tvTimeFile.text = dateString
            if (data.isFavourite == 1) {
                binding.imgStar.setImageResource(R.drawable.ic_star)
            } else {
                binding.imgStar.setImageResource(R.drawable.ic_star_un_select)
            }
        }
    }
}