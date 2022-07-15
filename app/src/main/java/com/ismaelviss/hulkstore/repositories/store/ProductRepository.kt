package com.ismaelviss.hulkstore.repositories.store

import com.ismaelviss.hulkstore.domain.model.ResultGet
import com.ismaelviss.hulkstore.domain.model.ResultProcess
import com.ismaelviss.hulkstore.domain.repositories.IProductRepository
import com.ismaelviss.hulkstore.services.product.model.Order
import com.ismaelviss.hulkstore.services.product.model.Product
import com.ismaelviss.hulkstore.utils.Log

class ProductRepository(private val productService: IProductService) : IProductRepository {
    private val tag = "ProductRepository"

    override fun products(token: String): ResultGet<List<Product>> {
        try {
            val result = productService.products(token)

            result.onSuccess {
                return if (it.isNotEmpty())
                    ResultGet.Success(it)
                else
                    ResultGet.Empty()
            }.onFailure {
                throw it
            }
        }
        catch (ex: Exception) {
            Log.e(tag, ex)
        }

        return ResultGet.Error(Exception("Error al conectar al servicio"))
    }

    override fun payOrder(token: String, orders: List<Order>): ResultProcess<List<Order>> {
        try {
            val result = productService.orders(token, orders)

            result.onSuccess {
                return ResultProcess.Success(it.elements?.toList())
            }.onFailure {
                throw it
            }
        }
        catch (ex: Exception) {
            Log.e(tag, ex)
        }

        return ResultProcess.Error(Exception("Error al conectar al servicio"))
    }
}