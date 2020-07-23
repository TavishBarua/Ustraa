package com.happilyunmarried.ustraa.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.happilyunmarried.ustraa.Fragments.GenericFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle, categoryNumber: ArrayList<String>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val arrayListCategory: ArrayList<String> = categoryNumber

    override fun getItemCount(): Int {
        return arrayListCategory.size
    }

    override fun createFragment(position: Int): Fragment {
        return GenericFragment.newInstance(position + 1, arrayListCategory[position])
    }
}