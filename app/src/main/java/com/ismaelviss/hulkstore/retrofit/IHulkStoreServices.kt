package com.ismaelviss.hulkstore.retrofit

import com.ismaelviss.hulkstore.services.login.model.UserRequest
import com.ismaelviss.hulkstore.services.login.model.UserResponse
import com.ismaelviss.hulkstore.services.product.model.Orders
import com.ismaelviss.hulkstore.services.product.model.Products
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface IHulkStoreServices {

    @POST("auth/login")
    fun login(@Body body: UserRequest) : Call<UserResponse>

    @GET("product")
    fun products(@Header("Authorization") authorization: String) : Call<Products>

    @POST("store/order")
    fun order(@Header("Authorization") authorization: String,@Body orders: Orders) : Call<Orders>
}