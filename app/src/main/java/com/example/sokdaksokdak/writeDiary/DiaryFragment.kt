package com.example.sokdaksokdak.writeDiary

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.lifecycle.ViewModelProvider
import com.example.sokdaksokdak.Diary.CalendarFragment
import com.example.sokdaksokdak.Diary.CalendarViewModel
import com.example.sokdaksokdak.databinding.FragmentDiaryBinding
import java.time.LocalDate

class DiaryFragment : Fragment() {
    //MVVM Pattern - View와 ViewModel의 Binding
    private lateinit var binding: FragmentDiaryBinding
    private lateinit var writeDiaryViewModel: WriteDiaryViewModel
    private lateinit var calendarViewModel: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        val Date = LocalDate.now()
        binding.monthTextView.text = Date.toString().split("-")[1]
        binding.dayTextView.text = Date.toString().split("-")[2]


        writeDiaryViewModel = ViewModelProvider(this).get(WriteDiaryViewModel::class.java)
        calendarViewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
        // Diary Table 비우기 - 확인을 위함
        // writeDiaryViewModel.deleteData()

        // 사용자의 keyword 추천 여부 반영 - 예외 처리

        // 앱 실행 시, 현재 날짜에 대한 DB Data 존재 및 작성 완료 상태 확인
        // -> 존재하지 않을 때
        //    -> insertData
        //    => 존재할 때의 순서 시행
        // -> 존재
        //    -> keyword & 내용 모두 작성 완료된 상태인지 확인
        //    -> keyword 만 갱신 된 상태일 때

        // 사용자가 keyword 를 수정했을 때
        // 실시간 DB 반영


        // checkDataExists
        /**
         * 1. 오늘 날짜에 해당하는 일기 데이터가 존재하는지 확인
         *    1.1. 없는 경우 - 새로운 데이터 추가
         * */
        if (!writeDiaryViewModel.checkDataExists()){ // 오늘의 Data 존재하지 않을 때
            writeDiaryViewModel.insertData()
        }


        // showKeyword
        /**
         * keyword & 내용 모두 작성 완료된 상태라면
         * EditText 가 아닌 TextView 로 화면에 표시
         * */

        val shared = requireActivity().getSharedPreferences("keyword", Context.MODE_PRIVATE)
        val key = shared.getBoolean("isKeyword",true)
        Log.i("키워드 추천 여부", key.toString())

        if (writeDiaryViewModel.checkDiaryCompleted()){
            // 작성 완료 버튼 없는 것으로 취급
            binding.diaryDoneBtn.visibility = View.GONE

            // 일기 작성 칸 TextView 로 데이터 표시만. EditText 는 없는 것으로 취급
            binding.diaryTextView.visibility = View.VISIBLE
            binding.diaryEditText.visibility = View.GONE
            binding.diaryTextView.setText(writeDiaryViewModel.showContent())

            binding.keywordEditView.visibility = View.GONE
            binding.keywordTextView.visibility = View.VISIBLE
            binding.keywordTextView.setText(writeDiaryViewModel.showKeyword(key))
        } else {
            // showKeyword
            binding.keywordEditView.setText(writeDiaryViewModel.showKeyword(key))

            // 추천 키워드를 화면에 표시
            // 사용자가 keyword 를 수정(또는 새로 입력)했을 때 -> 실시간 DB update
            binding.keywordEditView.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int,
                    after: Int,
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    val keyword = s.toString()
                    writeDiaryViewModel.setKeyword(keyword)
                }
            })

            // newDiaryData
            /**
             * 사용자가 일기 작성 완료 버튼 눌렀을 때 - DB update
             *
             * */
            binding.diaryDoneBtn.setOnClickListener {
                val keyword = binding.keywordEditView.text.toString()
                val content = binding.diaryEditText.text.toString()

                writeDiaryViewModel.newDiaryData(keyword, content)

                // 일기 작성 완료 후
                // 버튼 없애기
                // keyword, content: EditView -> TextView 변경
                binding.diaryDoneBtn.visibility = View.GONE

                binding.diaryTextView.visibility = View.VISIBLE
                binding.diaryEditText.visibility = View.GONE
                binding.diaryTextView.setText(content)

                binding.keywordEditView.visibility = View.GONE
                binding.keywordTextView.visibility = View.VISIBLE
                binding.keywordTextView.setText(writeDiaryViewModel.showKeyword(key))


            }

        }
        //캘린더를 클릭시 ClickListener로 하여금 CalendarDialog 불러올것
        binding.btnforCal.setOnClickListener{
            val datePickerFragment = CalendarFragment()
            val supportFragment = requireActivity().supportFragmentManager
            supportFragment.setFragmentResultListener(
                "KEY",
                viewLifecycleOwner
            ){
                    resultKey, bundle->
                if(resultKey == "KEY"){
                    //불러오는것 성공시, 날짜를 원하는 형식에 맞게 가공한 후 UI에 binding

                    val selectedDate = bundle.getString("SELECTED_DATE")?.split("-")
                    Log.e("loggg", selectedDate.toString())
                    val day = selectedDate?.get(0)
                    val month = selectedDate?.get(1)
                    val year = selectedDate?.get(2)
                    val date: String = year+"-"+month+"-"+day
                    if (day != null) {
                        Log.e("loggg", date)
                    }
                    if (month != null) {
                        Log.e("log", month)
                    }

                    binding.monthTextView.text = month
                    binding.dayTextView.text = day

                    //날짜별 일기 키워드와 내용 불러와서 binding
                    binding.diaryTextView.visibility = View.VISIBLE
                    binding.diaryEditText.visibility = View.GONE
                    binding.diaryTextView.setText(calendarViewModel.showDateContent(date))

                    binding.keywordEditView.visibility = View.GONE
                    binding.keywordTextView.visibility = View.VISIBLE
                    binding.keywordTextView.setText(calendarViewModel.showDateKeyWord(date))
                }
                else{
                    //에러 확인 로그
                    Log.e("log", "fail")
                }
            }
            datePickerFragment.show(supportFragment,"CalendarFragment")


        }

        return binding.root
    }

}