package com.ismaelviss.hulkstore.services.product

import com.ismaelviss.hulkstore.repositories.store.IProductService
import com.ismaelviss.hulkstore.retrofit.IHulkStoreServices
import com.ismaelviss.hulkstore.services.product.model.*
import com.ismaelviss.hulkstore.utils.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class ProductService(private val services: IHulkStoreServices) : IProductService {

    private val tag = "ProductService"

    override fun products(token: String): Result<List<Product>> {
        return try {
            val response = services.products(token).execute()

            if (response.code() == 200)
                Result.success(response.body()!!)
            else
                throw Exception("Error ${response.code()} - ${response.errorBody()}")

        } catch (ex: Exception) {
            Log.e(tag, ex)
            Result.failure(ex)
        }
    }

    override fun orders(token: String, orders: List<Order>): Result<Orders> {
        return try {
            //Log.d(tag, OrdersRequest(orders).toJson())
            val request = OrdersRequest(orders.map { x -> OrderRequest(x.productID, x.quantity, x.shipDate, x.status, x.complete) })

            val requestBody = Orders(orders).toJson().toRequestBody("application/json".toMediaType())
            val response = services.order(token, request).execute()

            if (response.code() == 200)
                Result.success(response.body()!!)
            else
                throw Exception("Error ${response.code()} - ${response.errorBody()}")

        } catch (ex: Exception) {
            Log.e(tag, ex)
            Result.failure(ex)
        }
    }
}