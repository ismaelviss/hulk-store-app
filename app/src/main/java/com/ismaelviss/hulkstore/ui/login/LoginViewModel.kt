@file:OptIn(DelicateCoroutinesApi::class)

package com.ismaelviss.hulkstore.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.ismaelviss.hulkstore.R
import com.ismaelviss.hulkstore.domain.repositories.ILoginRepository
import com.ismaelviss.hulkstore.utils.ValidString.Companion.isPasswordValid
import com.ismaelviss.hulkstore.utils.ValidString.Companion.isUserNameValid
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginViewModel
    @Inject constructor(private val loginRepository: ILoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { loginRepository.login(username, password) }

            if (result.isSuccess) {
                _loginResult.value = LoginResult(success = LoggedInUserView(result.getOrNull()?.firstName?:""))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }


}