package com.example.sokdaksokdak.Factory

import android.content.Context
import android.content.Intent
import com.example.sokdaksokdak.*


class CloverThemeFactory() : ThemeFactory {
    override fun createNaviActivity(context: Context) {
        val intent = Intent(context, PolaNaviActivity::class.java)
        context.startActivity(intent)
    }

    override fun createSplashActivity(context: Context){
        val intent = Intent(context, PolaSplashActivity::class.java)
        context.startActivity(intent)
    }

}