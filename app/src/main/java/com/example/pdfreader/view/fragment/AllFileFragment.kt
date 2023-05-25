package com.example.pdfreader.view.fragment

import android.os.Bundle
import android.util.Log
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.FragmentAllFileBinding
import com.example.pdfreader.task.ICallbackLoadFile
import com.example.pdfreader.task.LoadPdfFile
import com.example.pdfreader.task.TagLoadfile


class AllFileFragment : BaseFragment<FragmentAllFileBinding>(FragmentAllFileBinding::inflate) {
    private val listData :ArrayList<DataFile> = ArrayList()
    private var loadPdfFile: LoadPdfFile? = null

    companion object {
        fun newInstance(): AllFileFragment {
            val fragment = AllFileFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun initView() {

    }


    override fun initObserver() {

    }

    override fun getData() {
        loadPdfFile = LoadPdfFile(requireActivity(), object : ICallbackLoadFile {
            override fun callbackLoadFile(tag: TagLoadfile, data: ArrayList<DataFile>) {
                listData.clear()
                listData.addAll(data)
                Log.d("Listdata",listData.size.toString())
                Log.d("Listdata",data.size.toString())
            }
        })
        loadPdfFile?.execute()
    }
}