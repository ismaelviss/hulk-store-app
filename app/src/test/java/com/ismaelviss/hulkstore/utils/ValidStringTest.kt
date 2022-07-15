package com.ismaelviss.hulkstore.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidStringTest {

  @Test
  fun valid_username_success() {
       assertThat(ValidString.isUserNameValid("ismaelviss")).isTrue()
  }

    @Test
    fun valid_password_success() {
        assertThat(ValidString.isPasswordValid("3s4lv4t1")).isTrue()
    }
}