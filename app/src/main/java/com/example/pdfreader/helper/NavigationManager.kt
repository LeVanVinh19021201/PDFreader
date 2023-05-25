package com.example.pdfreader.helper

import android.annotation.SuppressLint
import android.app.Activity
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.pdfreader.R

class NavigationManager() :
    FragmentManager.OnBackStackChangedListener {
    protected lateinit var mActivity: Activity
    lateinit var mFragmentManager: FragmentManager
    private var mContentId: Int? = null

    companion object {
        fun getInstance(): NavigationManager = NavigationManagerHolder.navigationManagerHolder
    }

    private object NavigationManagerHolder {
        @SuppressLint("StaticFieldLeak")
        val navigationManagerHolder = NavigationManager()
    }

    fun init(activity: Activity, fragmentManager: FragmentManager, @IdRes contentId: Int) {
        mActivity = activity
        mFragmentManager = fragmentManager
        mContentId = contentId
        mFragmentManager.addOnBackStackChangedListener(this)
    }

    fun popBackStack() {
        mActivity.onBackPressed()
    }

    inline fun <reified T : Fragment> getFragmentInBackStack(mFragmentManager: FragmentManager): T? {
        mFragmentManager.fragments.forEach {
            val findMainFragment = it
            if (findMainFragment is T) {
                return findMainFragment
            }
        }
        return null
    }

    fun openFragment(
        fragment: Fragment,
        isReplace: Boolean = false,
        @AnimRes enter: Int,
        @AnimRes exit: Int,
        @AnimRes popEnter: Int,
        @AnimRes popExit: Int
    ) {
        try {
            mFragmentManager.commit {
                if (enter != 0 || exit != 0 || popEnter != 0 || popExit != 0) {
                    setCustomAnimations(enter, exit, popEnter, popExit)
                }
                mContentId?.let {
                    if (isReplace)
                        replace(it, fragment)
                    else
                        add(it, fragment, fragment::class.simpleName)

                }
                addToBackStack((2147483646.0 * Math.random()).toInt().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openFragment(
        fragment: Fragment,
        isReplace: Boolean = false,
    ) {
        openFragment(
            fragment, isReplace,
            R.anim.slide_in_left,
            R.anim.opacity_1_to_0,
            0,
            R.anim.slide_out_right
        )
    }

    fun openFragmentBottomUp(
        fragment: Fragment,
        isReplace: Boolean = false,
    ) {
        openFragment(
            fragment, isReplace,
//            R.anim.slide_in_up,
//            R.anim.opacity_1_to_0,
//            0,
//            R.anim.slide_out_down
        )
    }

    override fun onBackStackChanged() {

    }
}