package com.example.covid19info.Interface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("homemenucategories/v1.0.1?device_type=mob")
    fun all_categories():Call<Any>

    @GET("catalog/v1.0.1")
    fun all_product(@Query(value="category_id")categoryId:String):Call<Any>

}