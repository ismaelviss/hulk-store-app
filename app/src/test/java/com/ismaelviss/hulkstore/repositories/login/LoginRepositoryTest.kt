package com.ismaelviss.hulkstore.repositories.login

import com.ismaelviss.hulkstore.services.login.model.UserRequest
import com.ismaelviss.hulkstore.services.login.model.UserResponse
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import com.google.common.truth.Truth.assertThat

class LoginRepositoryTest {

    private lateinit var loginService: ILoginService

    @Before
    fun init() {
        loginService = Mockito.mock(ILoginService::class.java)
    }

    @Test
    fun test_login() {
        //val userRequest = UserRequest("esalvait", "1234567890")
        Mockito.`when`(loginService.login(UserRequest("esalvati", "1234567890"))).thenReturn(
            //Result.success(UserResponse("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlc2FsdmF0aSIsImlhdCI6MTY1NzQ5Nzk3MiwiZXhwIjoxNjU3NTMzOTcyfQ.t7BKQFbOv5xS7wWgV68XhQANWaPliASOuKh7MrphJMBWWOTbnTrj2hK9LX0b3iWo5yoVGu6fsGhRFBSPxc0evw"))
            Result.success(UserResponse("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.jYsfLIXC3Lif1mJHrW6Ri0XfCWPbnqPcfo5ZOWkOFDU8xQkNyPY4mulUFLdyS8gf3jG9ZEh3B9Ehq0tjypY9xg"))
        )

        val loginRepository = LoginRepository(loginService)

        val result = loginRepository.login("esalvati", "1234567890")

        assertThat(result.isSuccess).isTrue()
    }
}