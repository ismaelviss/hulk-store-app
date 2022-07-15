package com.ismaelviss.hulkstore.services.product.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Products() : ArrayList<Product>() {
    var elements: Collection<Product>? = null

    constructor(elements: Collection<Product>) : this() {
        this.elements = elements
    }

    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<Products>(json)
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Product (
    val id: Long? = null,
    val category: String? = null,
    val name: String? = null,
    val photoUrls: String? = null,
    val status: Boolean? = null,
    var quantity: Long? = null,
    val price: Double? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Category (
    val id: Long? = null,
    val name: String? = null
)
