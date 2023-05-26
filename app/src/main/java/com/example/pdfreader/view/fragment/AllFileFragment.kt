package com.example.pdfreader.view.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.FragmentAllFileBinding
import com.example.pdfreader.view.adapter.AllfileAdapter
import com.example.pdfreader.view.callback.ICallbackAllFile
import com.example.pdfreader.view.callback.TagAllFile
import com.example.pdfreader.view.viewmodel.AppViewModel
import com.example.pdfreader.view.viewmodel.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllFileFragment : BaseFragment<FragmentAllFileBinding>(FragmentAllFileBinding::inflate),
    ICallbackAllFile {
    private val viewModel: AppViewModel by viewModels()
    private val listData: ArrayList<DataFile> = ArrayList()
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

        binding.refreshLayout.setOnRefreshListener {
            getData()
            binding.refreshLayout.isRefreshing = false
        }
    }


    override fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it.status) {
                State.Status.GET_ALl_SUCCESS -> {
                    val data = it.data as ArrayList<DataFile>
                    listData.clear()
                    listData.addAll(data)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }
    override fun getData() {
        viewModel.getALlData()
    }

    override fun callbackALlFile(tag: TagAllFile, data: DataFile) {
        when (tag) {
            TagAllFile.ON_CLICK_FAVOURITE -> {
                val data = DataFile(
                    id = data.id,
                    path = data.path,
                    isFavourite = data.isFavourite,
                    isRecentFile = data.isRecentFile,
                    timeOpenRecent = data.timeOpenRecent,
                    timeClickFavourite = data.timeClickFavourite,
                    timeOpenFile = data.timeOpenFile
                )
                viewModel.updateDataFile(data)
            }

            TagAllFile.ON_CLICK_OPEN_FILE -> {
                val data = DataFile(
                    id = data.id,
                    path = data.path,
                    isFavourite = data.isFavourite,
                    isRecentFile = 1,
                    timeOpenRecent = data.timeOpenRecent,
                    timeClickFavourite = data.timeClickFavourite,
                    timeOpenFile = data.timeOpenFile
                )
                viewModel.updateDataFile(data)
            }
        }
    }
}