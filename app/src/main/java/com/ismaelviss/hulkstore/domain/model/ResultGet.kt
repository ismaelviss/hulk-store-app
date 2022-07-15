package com.ismaelviss.hulkstore.domain.model

import java.io.Serializable

/**
 * clase general para manejar respuesta de consultas
 */
sealed class ResultGet<out R> : Serializable {
    data class Loading(val data: Any? = null) : ResultGet<Nothing>()
    data class Error(val exception: Exception) : ResultGet<Nothing>()
    data class Success<T>(val data: T? = null) : ResultGet<T>()
    data class Empty(val data: Any? = null) : ResultGet<Nothing>()

}

sealed class ResultProcess<out R> : Serializable {
    data class Loading(val data: Any? = null) : ResultProcess<Nothing>()
    data class Error(val exception: Exception) : ResultProcess<Nothing>()
    data class Success<T>(val data: T? = null) : ResultProcess<T>()
}