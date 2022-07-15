package com.ismaelviss.hulkstore.services.login

import androidx.annotation.OpenForTesting
import com.ismaelviss.hulkstore.repositories.login.ILoginService
import com.ismaelviss.hulkstore.retrofit.IHulkStoreServices
import com.ismaelviss.hulkstore.services.login.model.UserRequest
import com.ismaelviss.hulkstore.services.login.model.UserResponse
import com.ismaelviss.hulkstore.utils.Log

@OpenForTesting
class LoginService(private val services: IHulkStoreServices) : ILoginService {
    private val tag = "LoginService"

    override fun login(userRequest: UserRequest) : Result<UserResponse> {
        return try {
            val response = services.login(userRequest).execute()

            if (response.code() == 200 || response.code() == 201)
                Result.success(response.body()!!)
            else
                throw Exception("Error ${response.code()} - ${response.errorBody()}")

        } catch (ex: Exception) {
            Log.e(tag, ex)
            Result.failure(ex)
        }
    }
}