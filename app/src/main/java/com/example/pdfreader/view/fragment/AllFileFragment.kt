package com.example.pdfreader.view.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.FragmentAllFileBinding
import com.example.pdfreader.helper.PreferenceHelper
import com.example.pdfreader.task.ICallbackLoadFile
import com.example.pdfreader.task.LoadPdfFileTask
import com.example.pdfreader.task.TagLoadfile
import com.example.pdfreader.utils.Const
import com.example.pdfreader.view.adapter.AllfileAdapter
import com.example.pdfreader.view.callback.ICallbackAllFile
import com.example.pdfreader.view.callback.TagAllFile
import com.example.pdfreader.view.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllFileFragment : BaseFragment<FragmentAllFileBinding>(FragmentAllFileBinding::inflate),
    ICallbackAllFile {
    private val viewModel: AppViewModel by viewModels()
    private var adapter: AllfileAdapter? = null
    private val listData: ArrayList<DataFile> = ArrayList()
    private var loadPdfFile: LoadPdfFileTask? = null

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
        getDataDevice()
        binding.refreshLayout.setOnRefreshListener {
            getDataDevice()
            binding.refreshLayout.isRefreshing = false
        }
    }


    override fun initObserver() {
//        viewModel.state.observe(viewLifecycleOwner) {
//            when (it.status) {
//                State.Status.GET_ALl_LOADING -> {
//                    binding.wrapProgressBar.show()
//                    binding.progressBar.setIndeterminateDrawable(Circle())
//                }
//
//                State.Status.GET_ALl_SUCCESS -> {
//                    binding.wrapProgressBar.hide()
//                    val data = it.data as ArrayList<DataFile>
//                    listData.clear()
//                    listData.addAll(data)
//                    adapter?.notifyDataSetChanged()
//                }
//
//                State.Status.GET_ALl_FAIL ->{
//                    binding.wrapProgressBar.hide()
//                    Toast.makeText(requireContext(),"Fail",Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    override fun getData() {
//        viewModel.getALlData()
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

    private fun getDataDevice() {
        loadPdfFile = LoadPdfFileTask(
            requireActivity(),
            object : ICallbackLoadFile {
                override fun callbackLoadFile(tag: TagLoadfile, data: ArrayList<DataFile>) {
                    when (tag) {
                        TagLoadfile.LOAD_FILE_SUCCESS -> {
                            listData.clear()
                            listData.addAll(data)
                            adapter?.notifyDataSetChanged()
                        }
                    }
                }
            },
            viewModel
        )
        loadPdfFile?.execute()
    }
}