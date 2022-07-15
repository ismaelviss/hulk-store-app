package com.ismaelviss.hulkstore.repositories.store

import com.ismaelviss.hulkstore.services.product.model.Order
import com.ismaelviss.hulkstore.services.product.model.Orders
import com.ismaelviss.hulkstore.services.product.model.Product

interface IProductService {

    fun products(token: String) : Result<List<Product>>

    fun orders(token: String, orders: List<Order>) : Result<Orders>
}