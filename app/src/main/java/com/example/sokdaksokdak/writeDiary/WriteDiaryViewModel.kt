package com.example.sokdaksokdak.writeDiary

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.sokdaksokdak.database.AppDatabase

class WriteDiaryViewModel(application: Application): AndroidViewModel(application) {
    var recommendKeyword = RecommendKeyword()
    private var writeDiary = WriteDiary(application)

    public fun insertData() {
        writeDiary.insertDiary()
        println("successfully initialized diary data")
    }

    public fun newDiaryData(keyword:String, content:String) {
        writeDiary.newDiaryData(keyword, content)
    }

    public fun checkDataExists(): Boolean {
        return writeDiary.isDataExists()
    }

    public fun checkDiaryCompleted(): Boolean {
        val content = writeDiary.getDiaryContent()
        return if (content == "일기를 작성하세요."){
            false
        } else{
            Log.i("content", content)
            true
        }
    }

    /** showKeyword
     * keyword 만 갱신 된 상태일 때
     *
     * 1. 오늘 날짜에 해당하는 keyword 가져오기
     * 2. 가져온 keyword 가
     *    2.1. 초기 값일 때
     *         2.1.1. 사용자의 키워드 추천 여부 확인
     *             2.1.1.1. 추천 - random 으로 키워드 가져오기
     *             2.1.1.2. 비추천 - "키워드를 입력하세요."로 변경
     *         2.1.1. 가져온 keyword DB 에 update
     *    2.2. 이미 갱신된 값일 때 - 바로 반환
     * 3. 화면에 keyword 표시
     *
     * */
    public fun showKeyword(key:Boolean): String{
        var keywordDB = writeDiary.getKeyword()

        Log.i("현재 저장 keyword: ", keywordDB)

        return if (keywordDB == "키워드를 선택하세요."){
            // 키워드 추천 여부
            //  1. SharedPreference 에서 사용자의 키워드 추천 여부 확인
            //     1.1 추천 - random 값 가져오기
            //     1.2 비추천 - "키워드를 입력하세요."로 수정

            if (key){
                Log.i("keyword","Get Random Keyword")
                getRandomKeyword()
            }else{
                val temp = ""
                Log.i("keyword","Write your own Keyword")
                writeDiary.updateKeyword(temp)
                return temp
            }

        } else{
            return keywordDB
        }
        // 만약 DB에 저장된 keyword 가
        // 기본값(키워드를 선택하세요.)이면 getRandomKeyword 호출
        // 기본값이 아니면, getKeyword 호출
    }

    public fun showContent(): String{
        return writeDiary.getDiaryContent()
    }

    private fun getRandomKeyword(): String {
        val keyword = recommendKeyword.randomKeyword()
        writeDiary.updateKeyword(keyword)
        return keyword
    }

    /*fun deleteData() {
        writeDiary.deleteData()
    }*/

    fun setKeyword(keyword: String) {
        writeDiary.updateKeyword(keyword)
    }



}