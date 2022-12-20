package com.example.sokdaksokdak.Login

import android.content.Context

class UserLogin(
    private var socialLogin: SocialLogin
) {
    fun login(context: Context?) {
        socialLogin.Login(context)
    }
}