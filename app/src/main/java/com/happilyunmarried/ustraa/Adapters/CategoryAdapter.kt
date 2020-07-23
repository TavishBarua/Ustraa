package com.happilyunmarried.ustraa.Adapters

import CategoryDto
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.happilyunmarried.ustraa.Interface.TabSelectInterface
import com.happilyunmarried.ustraa.MainActivity
import com.happilyunmarried.ustraa.R
import com.squareup.picasso.Picasso


class CategoryAdapter(categoryDto: CategoryDto, activity: MainActivity, context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    private var mCategoryDto: CategoryDto? = categoryDto
    val mMainActivity = activity
    val mContext = context
    private var selectedTab = 0
    private var animateFirstTab = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_category_tabs, parent, false)
        return CategoryViewHolder(view)
    }


    override fun getItemCount(): Int {
        return mCategoryDto?.categoryList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onBindViewHolder(categoryViewHolder: CategoryViewHolder, pos: Int) {
        categoryViewHolder.productTitle?.text = mCategoryDto?.categoryList?.get(pos)?.categoryName
        Picasso.get()
            .load(mCategoryDto?.categoryList?.get(pos)?.categoryImage)
            .placeholder(R.drawable.ic_launcher_background)
            .into(categoryViewHolder.productImg)

        if (animateFirstTab) {
            var animation = AnimationUtils.loadAnimation(mContext, R.anim.scale)
            animation.reset()
            categoryViewHolder.cv_category_item?.clearAnimation()
            categoryViewHolder.cv_category_item?.startAnimation(animation)
            animateFirstTab = false
        }
        categoryViewHolder.cv_category_item?.setOnClickListener {
            mMainActivity.fragmentItemChange(pos)
        }
    }

    fun setSelectedTab(selectedTab: Int) {
        val currentSelectedTab: Int = selectedTab
        if (currentSelectedTab != selectedTab) {
            notifyItemChanged(currentSelectedTab)
            notifyItemChanged(selectedTab)
        }
        this.selectedTab = selectedTab
    }

    fun animateFirstTab(position: Int) {
        animateFirstTab = true
        notifyItemChanged(position)
    }


    inner class CategoryViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var productTitle: AppCompatTextView? =
            itemView?.findViewById<AppCompatTextView?>(R.id.tv_category)
        var productImg: AppCompatImageView? =
            itemView?.findViewById<AppCompatImageView?>(R.id.img_category)
        var cv_category_item: CardView? =
            itemView?.findViewById<CardView?>(R.id.cv_category_item)

    }


}