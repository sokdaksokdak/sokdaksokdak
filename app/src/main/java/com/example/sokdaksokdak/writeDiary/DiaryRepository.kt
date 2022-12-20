package com.example.sokdaksokdak.writeDiary

import android.app.Application
import com.example.sokdaksokdak.database.AppDatabase
import com.example.sokdaksokdak.database.Diary

class DiaryRepository(application: Application) {
    private val database = AppDatabase.getInstance(application)!!
    private val diaryDao = database.diaryDao()

    fun getAll(): List<Diary>{
        return diaryDao.getAll()
    }

    fun insertData(keyword:String, content:String) {
        val r = Runnable {
            diaryDao.insertDiaryData(keyword, content)
        }

        val thread = Thread(r)
        thread.start()
    }

    fun updateData(keyword: String, content: String){
        val r = Runnable {
            diaryDao.updateDiaryData(keyword, content)
        }

        val thread = Thread(r)
        thread.start()
    }

    fun getTodayKeyword(): String {
        var todayKeyword: String = "Get Today Keyword"

        val r = Runnable {
            todayKeyword = diaryDao.getTodayKeyword()
            println("\ntodayKeyword from DB in Runnable: "+todayKeyword)
        }

        val thread = Thread(r)
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            println("thread join exception in getTodayKeyword")
        }

        return todayKeyword
    }

    fun isDataExists(): Boolean {
        var isExists: Boolean = false
        println("isExists: " + isExists)

        val r = Runnable {
            isExists = diaryDao.isDataExist()
            println("isExists: " + isExists)
        }

        val thread = Thread(r)
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            println("thread join exception in isDataExists")
        }
        println("isExist: " + isExists)
        return isExists
    }

    /*fun deleteData() {
        val r = Runnable {
            diaryDao.deleteData()
        }
        val thread = Thread(r)
        thread.start()
    }*/

    fun getDiaryContent(): String {
        var diaryContent: String = "Get Today Diary Content"

        val r = Runnable {
            diaryContent = diaryDao.getDiaryContent()
            println("\ndiaryContent from DB in Runnable: "+diaryContent)
        }

        val thread = Thread(r)
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            println("thread join exception in getDiaryContent")
        }

        return diaryContent

    }

}