package com.happilyunmarried.ustraa

import CategoryDto
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import androidx.viewpager2.widget.ViewPager2
import com.example.covid19info.Interface.ApiInterface
import com.example.covid19info.Utils.RetroResponse.ApiClient
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.happilyunmarried.ustraa.Adapters.CategoryAdapter
import com.happilyunmarried.ustraa.Adapters.ProductAdapter
import com.happilyunmarried.ustraa.Adapters.ViewPagerAdapter
import com.happilyunmarried.ustraa.Models.ProductDto
import com.happilyunmarried.ustraa.Utils.MyLinearSnapHelper
import com.happilyunmarried.ustraa.Utils.ViewPagerHelper
import retrofit2.Call
import retrofit2.Response
import java.net.SocketTimeoutException


class MainActivity : AppCompatActivity() {

    private lateinit var apiInterface: ApiInterface
    private lateinit var category_RecyclerView: RecyclerView
    private var mCategoryDto: CategoryDto? = null
    private var layoutManager_Category: RecyclerView.LayoutManager? = null

    var myViewPager2: ViewPager2? = null
    private lateinit var arrayListCategory: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        category_RecyclerView = findViewById(R.id.rv_category_tabs)
        layoutManager_Category = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        category_RecyclerView.layoutManager = layoutManager_Category
        category_RecyclerView.setHasFixedSize(true)
        category_RecyclerView.isVerticalScrollBarEnabled = false
        category_RecyclerView.isHorizontalScrollBarEnabled = false
        category_RecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        arrayListCategory = ArrayList()
        myViewPager2 = findViewById(R.id.view_pager)
        myViewPager2?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val smoothScroller = MyLinearSnapHelper()
        smoothScroller.attachToRecyclerView(category_RecyclerView)
        fetchCategories()

    }

    fun fetchCategories() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        val call = apiInterface.all_categories()
        try {
            call.enqueue(object : retrofit2.Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    if (t is SocketTimeoutException)
                        print("Socket Time out" + t.stackTrace)
                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val str1 = Gson().toJson(response.body())
                            mCategoryDto = Gson().fromJson(str1, CategoryDto::class.java)
                            if (mCategoryDto != null) {
                                for (category in mCategoryDto?.categoryList!!) {
                                    arrayListCategory.add(category.categoryId)
                                }
                                ViewPagerHelper(
                                    supportFragmentManager,
                                    this@MainActivity,
                                    lifecycle,
                                    arrayListCategory,
                                    mCategoryDto!!
                                )
                            }

                        }

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }

                }

            })
        } catch (ex: Exception) {
            print("Tavish  " + ex.printStackTrace())
        }

    }

    fun fragmentItemChange(item: Int) {
        myViewPager2?.currentItem = item

    }
}

