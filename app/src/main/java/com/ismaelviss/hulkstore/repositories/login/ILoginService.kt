package com.ismaelviss.hulkstore.repositories.login

import com.ismaelviss.hulkstore.services.login.model.UserRequest
import com.ismaelviss.hulkstore.services.login.model.UserResponse

interface ILoginService {
    fun login(userRequest: UserRequest) : Result<UserResponse>
}