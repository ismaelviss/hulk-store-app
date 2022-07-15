package com.ismaelviss.hulkstore.repositories.login

import com.ismaelviss.hulkstore.domain.model.User
import com.ismaelviss.hulkstore.domain.repositories.ILoginRepository
import com.ismaelviss.hulkstore.services.login.model.UserRequest
import com.ismaelviss.hulkstore.utils.JwtDecode
import com.ismaelviss.hulkstore.utils.Log
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import javax.crypto.spec.SecretKeySpec
import javax.inject.Singleton


@Singleton
class LoginRepository(private val loginService: ILoginService) : ILoginRepository {
    private val tag = "LoginRepository"
    private var userResult: Result<User>? = null

    override fun login(userName: String?, password: String?): Result<User> {

        try {
            if (userResult == null || userResult?.isFailure == true) {
                val result =
                    loginService.login(UserRequest(username = userName, password = password))
                result.onSuccess { userResponse ->
                    val user = User()
                    user.token = userResponse.jwt!!

                    val claims = JwtDecode.decode("wRAhaK8aGMK43c3pOA9CVMQa5R3mlfR+HUJQjubRMBzpcsKOgn+HMApLzUDhyCh6HnnmOmU0BSJwB1Z/eqDLxA==", userResponse.jwt!!)


                    userResult = Result.success(user)
                    return userResult!!
                }.onFailure {
                    return Result.failure(it)
                }
            } else {
                return userResult!!
            }
        }
        catch (ex: Exception) {
            Log.e(tag, ex)
        }

        return Result.failure(Exception("Error al conectar al servicio"))
    }
}