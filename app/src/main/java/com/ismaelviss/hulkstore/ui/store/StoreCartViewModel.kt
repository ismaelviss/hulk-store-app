@file:OptIn(DelicateCoroutinesApi::class)

package com.ismaelviss.hulkstore.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismaelviss.hulkstore.domain.model.ResultGet
import com.ismaelviss.hulkstore.domain.model.ResultProcess
import com.ismaelviss.hulkstore.domain.model.User
import com.ismaelviss.hulkstore.domain.repositories.ILoginRepository
import com.ismaelviss.hulkstore.domain.repositories.IProductRepository
import com.ismaelviss.hulkstore.services.product.model.Order
import com.ismaelviss.hulkstore.services.product.model.Product
import com.ismaelviss.hulkstore.utils.Tools.Companion.formatDateService
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class StoreCartViewModel
@Inject constructor(
    private val loginRepository: ILoginRepository,
    private val productRepository: IProductRepository
): ViewModel() {

    val fullName: String
        get() = "${user?.firstName} ${user?.lastName}"

    private val _payment = MutableLiveData<ResultProcess<List<Order>>>()
    val payment: LiveData<ResultProcess<List<Order>>> = _payment

    private val _resultProducts = MutableLiveData<ResultGet<List<Product>>>()
    val resultProducts: LiveData<ResultGet<List<Product>>> = _resultProducts

    private val _amountTotal = MutableLiveData<Double>()
    val amountTotal: LiveData<Double> = _amountTotal

    var user: User? = null

    fun products() {
        GlobalScope.launch(Dispatchers.Main) {
            _resultProducts.value = ResultGet.Empty()

            if(_amountTotal.value == null)
                _amountTotal.value = 0.0

            listOrder = mutableListOf()

            val login = withContext(Dispatchers.IO) { loginRepository.login(null, null) }

            if (login.isSuccess) {
                user = login.getOrNull()

                val result = withContext(Dispatchers.IO) { productRepository.products(login.getOrNull()?.token!!) }

                _resultProducts.value = result
            }
        }
    }

    private var listOrder: MutableList<Order> = mutableListOf()


    fun addProduct(product: Product) {
        val order = listOrder.find { x -> x.productID == product.id }
        if (order != null) {

            if(order.quantity == null) order.quantity = 0

            order.quantity = order.quantity!! + 1
        }
        else {
            listOrder.add(Order(productID = product.id, quantity = 1, shipDate = formatDateService(Calendar.getInstance().time), status = "approved", complete = true))
        }

        calculateAmountTotal(product, true)
    }

    private fun calculateAmountTotal(product: Product, sum: Boolean) {


        if (sum)
            _amountTotal.value = _amountTotal.value!! + product.price!!
        else
            _amountTotal.value = _amountTotal.value!! - product.price!!
    }

    fun subProduct(product: Product) {
        val order = listOrder.find { x -> x.productID == product.id }
        if (order != null && order.quantity?:0 > 0) {
            order.quantity = order.quantity!! - 1
        }
        else {
            listOrder.removeIf { x -> x.productID == product.id }
        }

        calculateAmountTotal(product, false)
    }

    fun existsProduct(product: Product) : Order? {
        return listOrder.find { x -> x.productID == product.id }
    }

    fun pay() {
        GlobalScope.launch(Dispatchers.Main) {
            _payment.value = ResultProcess.Loading()

            val result = withContext(Dispatchers.IO) { productRepository.payOrder(user?.token?:"", listOrder.toList()) }

            _payment.value = result
        }
    }
}