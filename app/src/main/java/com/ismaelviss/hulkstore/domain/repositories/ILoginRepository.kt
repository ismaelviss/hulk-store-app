package com.ismaelviss.hulkstore.domain.repositories

import com.ismaelviss.hulkstore.domain.model.User

interface ILoginRepository {

    fun login(userName: String?, password: String?) : Result<User>
}