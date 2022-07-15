package com.ismaelviss.hulkstore.services.login.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val mapperDefault = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRequest (
    val username: String? = null,
    val password: String? = null
) {
    fun toJson(): String = mapperDefault.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapperDefault.readValue<UserRequest>(json)
    }
}
