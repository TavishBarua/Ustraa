package com.example.covid19info.Utils.RetroResponse

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    val BASE_URL = "https://backend.ustraa.com/rest/V1/api/"
     var retrofit: Retrofit?=null

  val interceptor = HttpLoggingInterceptor()


    private fun okClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }



    fun getApiClient():Retrofit{

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        if(retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }

}