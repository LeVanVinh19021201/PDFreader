package com.example.pdfreader.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.pdfreader.R
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.FragmentHomeBinding
import com.example.pdfreader.task.ICallbackLoadFile
import com.example.pdfreader.task.LoadPdfFileTask
import com.example.pdfreader.task.TagLoadfile
import com.example.pdfreader.view.adapter.ViewPagerAdapter
import com.example.pdfreader.view.viewmodel.AppViewModel
import com.example.pdfreader.view.viewmodel.State
import com.example.pdfreader.view.widget.CustomTab
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private lateinit var pagerAdapter: ViewPagerAdapter
    private val viewModel: AppViewModel by viewModels()
    private val listData: ArrayList<DataFile> = ArrayList()

    private val listText = listOf(
        R.string.tab_str_allfile,
        R.string.tab_str_recent,
        R.string.tab_str_bookmark,
    )

    private val listIcon = listOf(
        R.drawable.ic_home_unselect,
        R.drawable.ic_recent,
        R.drawable.ic_bookmark
    )

    override fun initView() {
        pagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle
        )
        binding.pager.adapter = pagerAdapter
        binding.pager.offscreenPageLimit = 3
        TabLayoutMediator(binding.tabLayout, binding.pager, false, false) { tab, position ->
            tab.customView = getCustomViewTab(
                ContextCompat.getDrawable(requireContext(), listIcon[position]),
                getString(listText[position])
            )
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        (pagerAdapter.getFragment(0) as AllFileFragment).getData()
                    }

                    1 -> {
                        (pagerAdapter.getFragment(1) as RecentFragment).getData()
                    }

                    2 -> {
                        (pagerAdapter.getFragment(2) as FavouriteFragment).getData()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            return ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    private fun getCustomViewTab(
        image: Drawable?,
        text: String,
    ): CustomTab {
        val tab = CustomTab(requireContext())
        tab.icon.setImageDrawable(image)
        tab.text.text = text
        return tab
    }

    override fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it.status) {
                State.Status.GET_ALl_SUCCESS -> {
                    val data = it.data as ArrayList<DataFile>
                    listData.clear()
                    listData.addAll(data)
                }
            }
        }
    }

    override fun getData() {
        viewModel.getALlData()
    }
}