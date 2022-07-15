package com.ismaelviss.hulkstore.utils

class ValidString {
    companion object {
        fun isUserNameValid(username: String): Boolean {
            return if (username.length > 5) {
                val pattern = "[a-z]+".toRegex()
                pattern.matches(username)
            } else {
                username.isNotBlank()
            }
        }

        fun isPasswordValid(password: String): Boolean {
            return password.length > 3
        }
    }
}