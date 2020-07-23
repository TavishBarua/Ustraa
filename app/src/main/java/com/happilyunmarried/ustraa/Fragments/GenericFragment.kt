package com.happilyunmarried.ustraa.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19info.Interface.ApiInterface
import com.example.covid19info.Utils.RetroResponse.ApiClient
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.happilyunmarried.ustraa.Adapters.ProductAdapter
import com.happilyunmarried.ustraa.Models.ProductDto
import com.happilyunmarried.ustraa.R
import retrofit2.Call
import retrofit2.Response
import java.net.SocketTimeoutException


/**
 * A simple [Fragment] subclass.
 * Use the [GenericFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenericFragment : Fragment() {

    private val ARG_SECTION_NUMBER = "section_number"
    private val CATEGORY_ID = "category_id"
    private var sectionNumber = 0
    private var categoryNumber: String? = null
    private var product_adapter: ProductAdapter? = null
    private lateinit var product_RecyclerView: RecyclerView

    private lateinit var apiInterface: ApiInterface

    private var mProductDto: ProductDto? = null
    private var layoutManager_Product: RecyclerView.LayoutManager? = null
    var mShimmerFrameLayout: ShimmerFrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectionNumber = if (arguments != null) arguments?.getInt(ARG_SECTION_NUMBER)!! else 1
        categoryNumber = if (arguments != null) arguments?.getString(CATEGORY_ID) else null

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_generic, container, false)
        product_RecyclerView = view.findViewById(R.id.rv_product_list)
        mShimmerFrameLayout = view.findViewById(R.id.shimmer_view_container)
        layoutManager_Product = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        product_RecyclerView.layoutManager = layoutManager_Product
        product_RecyclerView.setHasFixedSize(true)
        product_RecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        categoryNumber?.let { fetchProducts(it) }

        return view

    }

    fun fetchProducts(categoryId: String) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        val call = apiInterface.all_product(categoryId)
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
                            mProductDto = Gson().fromJson(str1, ProductDto::class.java)
                            if (mProductDto != null) {
                                product_adapter =
                                    context?.let { ProductAdapter(mProductDto!!, getDeviceScreenConfiguration()) }
                                product_RecyclerView.adapter = product_adapter
                                mShimmerFrameLayout?.stopShimmer()
                                mShimmerFrameLayout?.visibility = View.GONE
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

    val orientation: Int
        get() {

            if ((resources.displayMetrics?.widthPixels!! > resources.displayMetrics?.heightPixels!!)) {
                return ORIENTATION_LANDSCAPE
            } else {
                return ORIENTATION_PORTRAIT
            }
        }

    fun getDeviceScreenConfiguration(): Int {
        val screenSize = getString(R.string.str_regular)
        var landscape = false
        if (orientation == ORIENTATION_LANDSCAPE) {
            landscape = true
        }
        return if (screenSize == REGULAR && !landscape)
            REGULAR_SCREEN_PORTRAIT
        else if (screenSize == REGULAR && landscape)
            REGULAR_SCREEN_LANDSCAPE
        else if (screenSize == SMALL_TABLET && !landscape)
            SMALL_TABLET_PORTRAIT
        else if (screenSize == SMALL_TABLET && landscape)
            SMALL_TABLET_LANDSCAPE
        else if (screenSize == LARGE_TABLET && !landscape)
            LARGE_TABLET_PORTRAIT
        else if (screenSize == LARGE_TABLET && landscape)
            LARGE_TABLET_LANDSCAPE
        else if (screenSize == XLARGE_TABLET && !landscape)
            XLARGE_TABLET_PORTRAIT
        else if (screenSize == XLARGE_TABLET && landscape)
            XLARGE_TABLET_LANDSCAPE
        else
            REGULAR_SCREEN_PORTRAIT
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GenericFragment.
         */

        const val ORIENTATION_PORTRAIT = 0
        const val ORIENTATION_LANDSCAPE = 1

        const val REGULAR_SCREEN_PORTRAIT = 0
        const val REGULAR_SCREEN_LANDSCAPE = 1
        const val SMALL_TABLET_PORTRAIT = 2
        const val SMALL_TABLET_LANDSCAPE = 3
        const val LARGE_TABLET_PORTRAIT = 4
        const val LARGE_TABLET_LANDSCAPE = 5
        const val XLARGE_TABLET_PORTRAIT = 6
        const val XLARGE_TABLET_LANDSCAPE = 7

        const val REGULAR = "regular"
        const val SMALL_TABLET = "small_tablet"
        const val LARGE_TABLET = "large_tablet"
        const val XLARGE_TABLET = "xlarge_tablet"
        @JvmStatic
        fun newInstance(sectionNumber: Int, categoryNumber: String) =
            GenericFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(CATEGORY_ID, categoryNumber)
                }

            }
    }
}