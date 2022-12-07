package com.example.sokdaksokdak

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "c7795b54e50244f372d84a1c2663a365")

    }
}