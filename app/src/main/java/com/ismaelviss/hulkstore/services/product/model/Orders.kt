package com.ismaelviss.hulkstore.services.product.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.readValue


@JsonIgnoreProperties(ignoreUnknown = true)
class Orders() : ArrayList<Order>() {
    var elements: Collection<Order>? = null

    constructor(elements: Collection<Order>) : this() {
        this.elements = elements
    }
    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<Orders>(json)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Order (
    val id: Long? = null,

    @get:JsonProperty("productId")@field:JsonProperty("productId")
    val productID: Long? = null,

    var quantity: Long? = null,
    val shipDate: String? = null,
    val status: String? = null,
    val complete: Boolean? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
class OrdersRequest(
    elements: Collection<OrderRequest>
) : ArrayList<OrderRequest>(elements) {

    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<Orders>(json)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class OrderRequest (
    @get:JsonProperty("productId")@field:JsonProperty("productId")
    val productID: Long? = null,
    var quantity: Long? = null,
    val shipDate: String? = null,
    val status: String? = null,
    val complete: Boolean? = null
) {
    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<Orders>(json)
    }
}