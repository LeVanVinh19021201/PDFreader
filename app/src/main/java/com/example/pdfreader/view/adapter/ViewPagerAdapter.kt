package com.example.pdfreader.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pdfreader.view.fragment.AllFileFragment
import com.example.pdfreader.view.fragment.FavouriteFragment
import com.example.pdfreader.view.fragment.RecentFragment
import com.example.pdfreader.view.viewmodel.AppViewModel

class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {
    val listFragment = ArrayList<Fragment?>(listOf(null, null, null, null, null))
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment : Fragment? = if(listFragment[position] != null){
            listFragment[position]
        } else {
            listFragment[position] = when(position){
                0 -> AllFileFragment()
                1 -> RecentFragment()
                2 -> FavouriteFragment()
                else -> null
            }
            listFragment[position]
        }
        return fragment ?: Fragment()
    }
    fun getFragment(index: Int) : Fragment?{
        return listFragment.getOrNull(index)
    }
}