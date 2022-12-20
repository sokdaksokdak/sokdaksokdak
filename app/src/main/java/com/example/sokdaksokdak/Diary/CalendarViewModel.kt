package com.example.sokdaksokdak.Diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sokdaksokdak.writeDiary.WriteDiary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(application: Application): AndroidViewModel(application) {

    private var writeDiary = WriteDiary(application)

    public fun showDateContent(date: String): String{
        return writeDiary.getDateDiaryContent(date)
    }

    public fun showDateKeyWord(date:String): String{
        return writeDiary.getDateKeyword(date)
    }

    public fun checkDateDataExists(date:String): Boolean{
        return writeDiary.isDateDataExists(date)
    }


}