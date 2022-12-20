package com.example.sokdaksokdak.writeDiary

class RecommendKeyword() {
    private lateinit var keyList: ArrayList<String>

    init { // == 생성자
        init()
    }

    private fun init(){
        this.keyList = arrayListOf("하늘", "구름", "풍경", "고찰", "바다", "별", "카메라", "달", "하루", "동물",
            "과제", "감정", "나", "책", "시험", "연애", "공부", "일", "운동", "관심사",
            "핸드폰", "연예인", "노래", "플레이리스트", "핸드폰", "일상", "전화", "추억", "여행", "소풍",
            "약속", "우정", "사랑", "기록", "헤드폰", "감성", "노트북", "만남", "우연", "운명",
            "필름", "유행", "일상", "과거", "미래", "목표", "성공", "만년필", "오늘", "시간")
        println("keyword list size: " + this.keyList.size)
        /*
        * 1. 메모리 상에서 리스트에 365개의 단어 저장 -> 랜덤으로 추출하여 가져오기
        * 2. 앱 설치할 때, 동시에 Room DB의 Keyword 테이블에 365개 단어 넣는 initialize 과정 거치기
        *
        for (i:Int in 1..365){ // 365개 단어 포함한 리스트 구성 - 대신 숫자로 테스트
            this.keyList.add(i.toString())
        }*/
    }

    public fun randomKeyword(): String{
        return this.keyList[(Math.random()*(this.keyList.size)).toInt()]
    }
}