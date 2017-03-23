package com.shpt.core.rest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by poovarasanv on 6/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 6/2/17 at 9:02 AM
 */

interface Rest {

    @GET
    @Streaming
    fun getLayout(@Url url: String): Call<ResponseBody>


    @GET
    @Streaming
    fun getJsonArrayLayout(@Url url: String): Call<ResponseBody>


    @GET
    @Streaming
    fun getProductDetail(@Url url: String, @Query("productid") productid: Int): Call<ResponseBody>


    @GET
    @Streaming
    fun getProductSearch(@Url url: String, @Query("keyword") keyword: String): Call<ResponseBody>


    @POST
    @FormUrlEncoded
    fun addToCart(@Url url: String, @Field("product_id") productid: Int, @Field("quantity") quantity: Int = 1): Call<ResponseBody>
}
