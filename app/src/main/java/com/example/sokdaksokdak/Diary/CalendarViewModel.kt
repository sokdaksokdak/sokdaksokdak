package com.example.sokdaksokdak.Diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sokdaksokdak.database.AppDatabase
import com.example.sokdaksokdak.database.Diary
import com.example.sokdaksokdak.writeDiary.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(application: Application): AndroidViewModel(application) {





//    private val DiaryData : DiaryRepository
//    //private var currentdata = MutableLiveData<List<Diary>>()
//    val currentData : List<Diary>
//        get() = currentdata
//
//    init{
//        val diaryDao = AppDatabase.getInstance(application)!!.diaryDao()
//        DiaryData = DiaryRepository(application)
//    }
//
//    private fun getAll():LiveData<List<Diary>>{
//        return DiaryData.getAll()
//    }
//
//    fun readDate(date:Int){
//        viewModelScope.launch(Dispatchers.IO){
//            val data = DiaryData.readDateData(date)
//        }
//
//    }
//
//    fun setData(diary: List<Diary>){
//
//    }


}