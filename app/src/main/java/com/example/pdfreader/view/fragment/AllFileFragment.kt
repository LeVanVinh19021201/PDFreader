package com.example.pdfreader.view.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.FragmentAllFileBinding
import com.example.pdfreader.task.ICallbackLoadFile
import com.example.pdfreader.task.LoadPdfFileTask
import com.example.pdfreader.task.TagLoadfile
import com.example.pdfreader.view.adapter.AllfileAdapter
import com.example.pdfreader.view.callback.ICallbackAllFile
import com.example.pdfreader.view.callback.TagAllFile
import com.example.pdfreader.view.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllFileFragment : BaseFragment<FragmentAllFileBinding>(FragmentAllFileBinding::inflate),
    ICallbackAllFile {
    private val viewModel: AppViewModel by viewModels()
    private val listData: ArrayList<DataFile> = ArrayList()
    private var loadPdfFile: LoadPdfFileTask? = null
    private var adapter: AllfileAdapter? = null

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
        adapter = AllfileAdapter(listData, this)
        binding.rvAllFile.adapter = adapter
    }


    override fun initObserver() {

    }

    override fun getData() {
        loadPdfFile = LoadPdfFileTask(requireActivity(), object : ICallbackLoadFile {
            override fun callbackLoadFile(tag: TagLoadfile, data: ArrayList<DataFile>) {
                listData.clear()
                listData.addAll(data)
                adapter?.notifyDataSetChanged()
            }
        })
        loadPdfFile?.execute()
    }
    override fun callbackALlFile(tag: TagAllFile, data: DataFile) {
        when(tag){
            TagAllFile.ON_CLICK_FAVOURITE ->{
                val data = DataFile(
                    id = data.id,
                    path = data.path,
                    isFavourite = 1,
                    isRecentFile = data.isRecentFile,
                    timeOpenRecent = data.timeOpenRecent,
                    timeClickFavourite = data.timeClickFavourite,
                    timeOpenFile = data.timeOpenFile
                )
                viewModel.addFile(data)
            }

            TagAllFile.ON_CLICK_OPEN_FILE ->{
                val data = DataFile(
                    id = data.id,
                    path = data.path,
                    isFavourite =data.isFavourite ,
                    isRecentFile = 1,
                    timeOpenRecent = data.timeOpenRecent,
                    timeClickFavourite = data.timeClickFavourite,
                    timeOpenFile = data.timeOpenFile
                )
                viewModel.addFile(data)
            }
        }
    }
}