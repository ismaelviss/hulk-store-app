package com.ismaelviss.hulkstore.domain.model

class User(
    var id: Long? = null,
    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var userStatus: Long? = null,
    var token: String? = null
)