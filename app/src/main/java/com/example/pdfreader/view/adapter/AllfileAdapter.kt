package com.example.pdfreader.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader.R
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.LayoutItemPdfBinding
import com.example.pdfreader.utils.Const.PATTERN_FORMAT_TIME_HOME
import com.example.pdfreader.view.callback.ICallbackAllFile
import com.example.pdfreader.view.callback.TagAllFile
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

class AllfileAdapter(
    private val listData: ArrayList<DataFile>,
    private val callback: ICallbackAllFile,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllfileViewHolder(
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
        if (holder is AllfileViewHolder) {
            holder.bind(listData[position], position)
        }
    }

    inner class AllfileViewHolder(val binding: LayoutItemPdfBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataFile, position: Int) {
            binding.tvNameFile.text = File(data.path).name
            val formatter = SimpleDateFormat(PATTERN_FORMAT_TIME_HOME, Locale.getDefault())
            val dateString = formatter.format(File(data.path).lastModified())
            binding.tvTimeFile.text = dateString

            val file = File(data.path)
            val fileSizeInBytes = file.length()
            val fileSizeInKB = fileSizeInBytes / 1024.0
            val fileSizeInMB = fileSizeInKB / 1024.0
            val decimalFormat = DecimalFormat("#.##")
            val roundedNumber = decimalFormat.format(fileSizeInMB)
            binding.tvSize.text = roundedNumber.toString() + "MB"

            if (data.isFavourite == 1) {
                binding.imgStar.setImageResource(R.drawable.ic_star)
            } else {
                binding.imgStar.setImageResource(R.drawable.ic_star_un_select)
            }

            binding.imgStar.setOnClickListener {
                if (data.isFavourite == 0) {
                    data.isFavourite = 1
                    callback.callbackALlFile(TagAllFile.ON_CLICK_FAVOURITE, data)
                } else {
                    data.isFavourite = 0
                    callback.callbackALlFile(TagAllFile.ON_CLICK_FAVOURITE, data)
                }
                notifyItemChanged(position)
            }

            binding.root.setOnClickListener {
                callback.callbackALlFile(TagAllFile.ON_CLICK_OPEN_FILE, data)
            }
        }
    }
}