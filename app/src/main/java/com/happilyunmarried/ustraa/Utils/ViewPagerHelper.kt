package com.happilyunmarried.ustraa.Utils

import CategoryDto
import android.app.Activity
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.happilyunmarried.ustraa.Adapters.CategoryAdapter
import com.happilyunmarried.ustraa.Adapters.ViewPagerAdapter
import com.happilyunmarried.ustraa.Interface.TabSelectInterface
import com.happilyunmarried.ustraa.MainActivity
import com.happilyunmarried.ustraa.R


class ViewPagerHelper(fragmentManager: FragmentManager, activity: Activity,lifecycle:Lifecycle, tabList:List<String>,categoryDto: CategoryDto):ViewPager2.OnPageChangeCallback(),
    TabSelectInterface {
    private var activity:Activity
    private var from:Byte = 0
    private lateinit var recyclerViewTabs: RecyclerView
    private lateinit var viewPager2: ViewPager2
    //private var fragmentList:ArrayList<Fragment>
    private var mLifecycle:Lifecycle = lifecycle
    private var mCategoryDto:CategoryDto = categoryDto
    private var tabList:List<String>
    private lateinit var mCategoryNumber: ArrayList<String>
    private lateinit var viewPagerTabsAdapter: CategoryAdapter
    private var fragmentManager:FragmentManager
    private var isLive:Boolean = false
    private lateinit var linearLayoutManagerTabs: LinearLayoutManager

    init{
        this.fragmentManager = fragmentManager
        this.activity = activity
        this.tabList = tabList
        setupViewPager()
    }
    private fun setupViewPager() {
        recyclerViewTabs = activity.findViewById(R.id.rv_category_tabs)
        viewPager2 = activity.findViewById(R.id.view_pager)
        linearLayoutManagerTabs = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recyclerViewTabs.layoutManager = linearLayoutManagerTabs

        mCategoryNumber = ArrayList()
        populateCategories(mCategoryDto)
        setTabsAdapter()
        setFragmentsAdapter()
        viewPager2.registerOnPageChangeCallback(this)
    }
    private fun setTabsAdapter() {
        viewPagerTabsAdapter = CategoryAdapter(mCategoryDto, activity as MainActivity, activity)
        recyclerViewTabs.adapter = viewPagerTabsAdapter
    }
    private fun setFragmentsAdapter() {
        val viewPagerFragmentAdapter = ViewPagerAdapter(fragmentManager,mLifecycle,mCategoryNumber)
        viewPager2.adapter = viewPagerFragmentAdapter

    }

    override fun onPageScrolled(position:Int, positionOffset:Float, positionOffsetPixels:Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }
    override fun onPageSelected(position:Int) {
        super.onPageSelected(position)
        Log.e("SelectedPage", (position).toString())
        viewPagerTabsAdapter.setSelectedTab(position)
        animateFirstTab(position)
        linearLayoutManagerTabs.scrollToPositionWithOffset(position, 0)
    }
    override fun onPageScrollStateChanged(state:Int) {
        super.onPageScrollStateChanged(state)
    }

    override fun onTabSelected(position:Int) {
        viewPager2.setCurrentItem(position, true)

    }
    fun animateFirstTab(position:Int) {
        viewPagerTabsAdapter.animateFirstTab(position)
    }
    fun populateCategories(categoryDto:CategoryDto){
        for (category in categoryDto.categoryList) {
            mCategoryNumber.add(category.categoryId)
        }
    }
}