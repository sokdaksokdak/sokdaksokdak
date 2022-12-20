package com.example.sokdaksokdak.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_table")
    fun getAll(): List<Diary>

    @Insert
    fun insertDiary(diary: Diary)

    @Query("Select * From diary_table")
    fun getDiary() : Diary

    @Delete
    fun delete(diary: Diary)

    @Query("SELECT date FROM diary_table")
    fun getDate(): List<Int>

//    @Query("select diary_context from diary_table where date=date")
//    //@Query("select * from diary_table where date=:date")
//    fun getDateData(date:Int)

    @Query("select keyword from diary_table where date=:date")
    fun getDateKeyword(date:String): String

    @Query("select exists (select keyword from diary_table where date=:date)")
    fun isDateDataExist(date:String): Boolean

    @Query("select diary_context from diary_table where date=:date")
    //@Query("select * from diary_table where date=:date")
    fun getDateContent(date:String):String



    @Query("delete from diary_table")
    fun deleteData()

    @Query("INSERT INTO diary_table(keyword, date, diary_context) VALUES (:keyword, (select date('now', 'localtime')), :diary_context)")
    fun insertDiaryData(keyword: String, diary_context: String)

    @Query("UPDATE diary_table SET keyword=:keyword, diary_context=:diary_context WHERE date=(select date('now', 'localtime'))")
    fun updateDiaryData(keyword: String, diary_context: String)

    // 날짜로 keyword 가져오기
    @Query("select keyword from diary_table where date=(select date('now', 'localtime'))")
    fun getTodayKeyword(): String

    @Query("select exists (select keyword from diary_table where date=(select date('now', 'localtime')))")
    fun isDataExist(): Boolean

    @Query("select diary_context from diary_table where date=(select date('now', 'localtime'))")
    fun getDiaryContent(): String


}