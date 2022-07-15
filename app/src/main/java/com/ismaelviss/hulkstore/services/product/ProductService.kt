package com.ismaelviss.hulkstore.services.product

import com.ismaelviss.hulkstore.repositories.store.IProductService
import com.ismaelviss.hulkstore.retrofit.IHulkStoreServices
import com.ismaelviss.hulkstore.services.product.model.Order
import com.ismaelviss.hulkstore.services.product.model.Orders
import com.ismaelviss.hulkstore.services.product.model.Product
import com.ismaelviss.hulkstore.utils.Log

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
            val response = services.order(token, Orders(orders)).execute()

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