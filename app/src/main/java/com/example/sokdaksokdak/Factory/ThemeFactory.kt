package com.example.sokdaksokdak.Factory

import android.content.Context
import com.example.sokdaksokdak.writeDiary.DiaryFragment
import com.example.sokdaksokdak.MypageFragment

interface ThemeFactory{
    fun createNaviActivity(context: Context)
    fun createSplashActivity(context: Context)

}