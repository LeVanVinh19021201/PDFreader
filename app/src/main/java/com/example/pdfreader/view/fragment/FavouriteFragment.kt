package com.example.pdfreader.view.fragment

import androidx.fragment.app.viewModels
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.common.hide
import com.example.pdfreader.common.show
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.FragmentFavouriteBinding
import com.example.pdfreader.view.adapter.FavouriteAdapter
import com.example.pdfreader.view.callback.ICallbackFavourite
import com.example.pdfreader.view.viewmodel.AppViewModel
import com.example.pdfreader.view.viewmodel.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(FragmentFavouriteBinding::inflate),
    ICallbackFavourite {
    private val viewModel: AppViewModel by viewModels()
    private val listData: ArrayList<DataFile> = ArrayList()
    private var adapter: FavouriteAdapter? = null

    override fun initView() {
        adapter = FavouriteAdapter(listData, this)
        binding.rvFavourite.adapter = adapter

        binding.refreshLayout.setOnRefreshListener {
            getData()
            binding.refreshLayout.isRefreshing = false
        }
    }

    override fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it.status) {
                State.Status.GET_FAVOURITE_LOADING ->{

                }
                State.Status.GET_FAVOURITE_SUCCESS ->{
                    binding.imgNoData.hide()
                    val data =it.data as ArrayList<DataFile>?
                    listData.clear()
                    data?.let { it1 -> listData.addAll(it1) }
                    adapter?.notifyDataSetChanged()
                }

                State.Status.GET_FAVOURITE_FAIL->{
                    listData.clear()
                    adapter?.notifyDataSetChanged()
                    binding.imgNoData.show()
                }
            }
        }
    }

    override fun getData() {
        viewModel.getDataFavoutite()
    }

    override fun callbackFavourite(data: DataFile) {

    }

}