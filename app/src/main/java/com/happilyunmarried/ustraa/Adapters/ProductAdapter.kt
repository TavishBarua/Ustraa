package com.happilyunmarried.ustraa.Adapters

import android.content.Context
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.happilyunmarried.ustraa.Fragments.GenericFragment.Companion.LARGE_TABLET_LANDSCAPE
import com.happilyunmarried.ustraa.Fragments.GenericFragment.Companion.LARGE_TABLET_PORTRAIT
import com.happilyunmarried.ustraa.Fragments.GenericFragment.Companion.REGULAR_SCREEN_LANDSCAPE
import com.happilyunmarried.ustraa.Fragments.GenericFragment.Companion.REGULAR_SCREEN_PORTRAIT
import com.happilyunmarried.ustraa.Fragments.GenericFragment.Companion.XLARGE_TABLET_LANDSCAPE
import com.happilyunmarried.ustraa.Fragments.GenericFragment.Companion.XLARGE_TABLET_PORTRAIT
import com.happilyunmarried.ustraa.Models.ProductDto
import com.happilyunmarried.ustraa.R
import com.squareup.picasso.Picasso


class ProductAdapter(productDto: ProductDto,screenSize:Int) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var mProductDto: ProductDto? = productDto
    private var mScreenSize: Int? = screenSize

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.placeholder_product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mProductDto?.products?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(productViewHolder: ProductViewHolder, pos: Int) {
        productViewHolder.productTitle?.text = mProductDto?.products?.get(pos)?.name
        productViewHolder.productFinalPrice?.text =
            mProductDto?.products?.get(pos)?.finalPrice.toString()
        if (mProductDto?.products?.get(pos)?.finalPrice.toString() != mProductDto?.products?.get(pos)?.price.toString()) {
            val string = SpannableString(mProductDto?.products?.get(pos)?.price.toString())
            string.setSpan(StrikethroughSpan(), 0, string.length, 0)
            productViewHolder.productPrice?.text = string
            productViewHolder.ll_price?.visibility = View.VISIBLE
        }
        if (mProductDto?.products?.get(pos)?.rating!! > 0) {
            productViewHolder.productRating?.text =
                mProductDto?.products?.get(pos)?.rating.toString()
            productViewHolder.ll_rating?.visibility = View.VISIBLE
        }
        if (mScreenSize == REGULAR_SCREEN_PORTRAIT) {
            Picasso.get()
                .load(mProductDto?.products?.get(pos)?.imageUrls?.x240)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(productViewHolder.productImg)
        } else if (mScreenSize == LARGE_TABLET_LANDSCAPE) {
            Picasso.get()
                .load(mProductDto?.products?.get(pos)?.imageUrls?.x408)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(productViewHolder.productImg)
        }else if (mScreenSize == LARGE_TABLET_PORTRAIT) {
            Picasso.get()
                .load(mProductDto?.products?.get(pos)?.imageUrls?.x300)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(productViewHolder.productImg)
        }
    }


    inner class ProductViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){

        var productImg: AppCompatImageView? =
            itemView?.findViewById<AppCompatImageView?>(R.id.img_product)
        var productTitle: AppCompatTextView? =
            itemView?.findViewById<AppCompatTextView?>(R.id.txt_product_title)
        var productPrice: AppCompatTextView? =
            itemView?.findViewById<AppCompatTextView?>(R.id.txt_price)
        var productFinalPrice: AppCompatTextView? =
            itemView?.findViewById<AppCompatTextView?>(R.id.txt_final_price)
        var productRating: AppCompatTextView? =
            itemView?.findViewById<AppCompatTextView?>(R.id.txt_rating)
        var ll_price: LinearLayoutCompat? =
            itemView?.findViewById<LinearLayoutCompat?>(R.id.ll_price)
        var ll_rating: LinearLayoutCompat? =
            itemView?.findViewById<LinearLayoutCompat?>(R.id.ll_rating)

    }




}