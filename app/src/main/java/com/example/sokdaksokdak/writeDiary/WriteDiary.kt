package com.example.sokdaksokdak.writeDiary

import android.app.Application
import com.example.sokdaksokdak.database.AppDatabase

class WriteDiary(application: Application) {
    private val repository = DiaryRepository(application)

    private lateinit var keyword: String
    private lateinit var content: String
    // 날짜는? - (select datetime('now', 'localtime')

    init {
        this.keyword = "키워드를 선택하세요."
        this.content = "일기를 작성하세요."
    }

    /*public fun createTodayDiary() {
        insertDiary()
    }*/

    public fun getKeyword(): String{
        var keywordDB = repository.getTodayKeyword() // DB 에서 날짜로 keyword 가져오기
        // Diary Dao 이용
        return keywordDB
    }

    public fun updateKeyword(keyword:String){
        this.keyword = keyword
        updateDiary()
    }

    public fun newDiaryData(keyword:String, content:String){
        this.keyword = keyword
        this.content = content

        updateDiary() // 일기 작성 완료했을 때 update 하는 함수
    }
    public fun insertDiary(){
        repository.insertData("키워드를 선택하세요.", "일기를 작성하세요.")
    }

    // 일기 작성 완료했을 때 update 하는 함수
    public fun updateDiary(){
        repository.updateData(this.keyword, this.content)
        // DataBase 에 keyword 혹은 content update
    }

    fun isDataExists(): Boolean {
        return repository.isDataExists()

    }

    /*fun deleteData() {
        repository.deleteData()
    }*/

    fun getDiaryContent(): String {
        return repository.getDiaryContent()
    }
}