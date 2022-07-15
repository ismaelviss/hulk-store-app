package com.ismaelviss.hulkstore.domain.repositories

import com.ismaelviss.hulkstore.domain.model.ResultGet
import com.ismaelviss.hulkstore.domain.model.ResultProcess
import com.ismaelviss.hulkstore.services.product.model.Order
import com.ismaelviss.hulkstore.services.product.model.Product

interface IProductRepository {

    fun products(token: String) : ResultGet<List<Product>>

    fun payOrder(token: String, orders: List<Order>) : ResultProcess<List<Order>>
}