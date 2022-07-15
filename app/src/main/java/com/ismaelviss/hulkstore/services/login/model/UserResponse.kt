package com.ismaelviss.hulkstore.services.login.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.readValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserResponse (
    val jwt: String? = null
) {
    fun toJson(): String = mapperDefault.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapperDefault.readValue<UserResponse>(json)
    }
}