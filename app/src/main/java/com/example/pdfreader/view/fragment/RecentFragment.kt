package com.example.pdfreader.view.fragment

import androidx.fragment.app.viewModels
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.FragmentRecentBinding
import com.example.pdfreader.view.adapter.FavouriteAdapter
import com.example.pdfreader.view.adapter.RecentAdapter
import com.example.pdfreader.view.callback.ICallbackRecent
import com.example.pdfreader.view.callback.TagAllFile
import com.example.pdfreader.view.viewmodel.AppViewModel
import com.example.pdfreader.view.viewmodel.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>(FragmentRecentBinding::inflate),
    ICallbackRecent {
    private val viewModel: AppViewModel by viewModels()
    private val listData: ArrayList<DataFile> = ArrayList()
    private var adapter: RecentAdapter? = null

    override fun initView() {
        adapter = RecentAdapter(listData, this)
        binding.rvRecent.adapter = adapter

        binding.refreshLayout.setOnRefreshListener {
            getData()
            binding.refreshLayout.isRefreshing = false
        }
    }

    override fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it.status) {
                State.Status.GET_RECENT_LOADING ->{

                }
                State.Status.GET_RECENT_SUCCESS ->{
                    val data =it.data as ArrayList<DataFile>?
                    listData.clear()
                    data?.let { it1 -> listData.addAll(it1) }
                    adapter?.notifyDataSetChanged()
                }

                State.Status.GET_RECENT_FAIL->{

                }
            }
        }
    }

    override fun getData() {
        viewModel.getDataRecent()
    }

    override fun callbackRecent(tag: TagAllFile, data: DataFile) {

    }
}