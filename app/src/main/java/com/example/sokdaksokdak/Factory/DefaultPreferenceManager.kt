package com.example.sokdaksokdak.Factory

import android.content.Context
import android.preference.PreferenceManager

class DefaultPreferenceManager(private val context:Context) {
    companion object{
        private const val themeType="themeType" //기본테마
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    fun setThemeType(type:String){
        editor.putString(themeType,type).commit()
    }

    fun getThemeType()=sharedPreferences.getString(themeType,"pola_theme")
}