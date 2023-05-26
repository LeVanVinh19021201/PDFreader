package com.example.pdfreader.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.pdfreader.R
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.databinding.FragmentHomeBinding
import com.example.pdfreader.view.adapter.ViewPagerAdapter
import com.example.pdfreader.view.viewmodel.AppViewModel
import com.example.pdfreader.view.widget.CustomTab
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: AppViewModel by viewModels()

    private lateinit var pagerAdapter: ViewPagerAdapter
    private val listText = listOf(
        R.string.tab_str_allfile,
        R.string.tab_str_recent,
        R.string.tab_str_bookmark,
    )

    private val listIcon = listOf(
        R.drawable.ic_allfile_selected,
        R.drawable.ic_recent,
        R.drawable.ic_bookmark
    )

    override fun initView() {
        pagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle
        )
        binding.pager.adapter = pagerAdapter
        binding.pager.offscreenPageLimit = 1
        TabLayoutMediator(binding.tabLayout, binding.pager, false, false) { tab, position ->
            Log.d("vvvvvvv",position.toString())
            tab.customView = getCustomViewTab(
                ContextCompat.getDrawable(requireContext(), listIcon[position]),
                getString(listText[position])
            )
        }.attach()
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

    }

    override fun getData() {

    }
}