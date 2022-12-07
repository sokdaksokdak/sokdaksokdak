package com.example.sokdaksokdak

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sokdaksokdak.databinding.FragmentDiaryBinding
import com.example.sokdaksokdak.databinding.FragmentMypageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient


class MypageFragment : Fragment() {
    var auth : FirebaseAuth?= null
    var googleSignInClient : GoogleSignInClient?= null
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMypageBinding.inflate(layoutInflater)
        binding.themeBtn.setOnClickListener {
            val intent = Intent(activity,ThemeChangeActivity::class.java)
            startActivity(intent)

        }

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                //google
                var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("72536997949-mgi6or994u3rn0bfnseto9ucpnd2bdft.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
                googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }

                // firebaseauth를 사용하기 위한 인스턴스 get
                auth = FirebaseAuth.getInstance()

                binding.mypageName.text = FirebaseAuth.getInstance().currentUser?.displayName
                binding.mypageBirth.visibility = View.INVISIBLE
                // 로그아웃
                binding.logoutBtn.setOnClickListener {
                    FirebaseAuth.getInstance().signOut()
                    googleSignInClient?.signOut()
                    Log.d("구글","로그아웃 성공")

                    var logoutIntent = Intent (context, PolaNaviActivity::class.java)
                    logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(logoutIntent)

                }
                //탈퇴
                binding.secessionBtn.setOnClickListener{
                    FirebaseAuth.getInstance().signOut()
                    googleSignInClient?.revokeAccess()
                    auth!!.currentUser?.delete()
                    Log.d("구글","탈퇴 성공")

                    var logoutIntent = Intent (context, PolaNaviActivity::class.java)
                    logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(logoutIntent)
                }
            } else if(tokenInfo != null) {
                //kakao
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        var name = user.kakaoAccount?.profile?.nickname.toString()
                        var birth = user.kakaoAccount?.birthday.toString()
                        //var birthyear = user.kakaoAccount?.birthyear.toString()
                        Log.d("이름","이름 : "+name)
                        binding.mypageBirth.visibility = View.VISIBLE
                        binding.mypageName.text = name
                        binding.mypageBirth.text = birth
                    }
                }
                // 로그아웃
                binding.logoutBtn.setOnClickListener {
                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            Log.d("카카오", " 로그아웃 실패")
                        }else {
                            Log.d("카카오", "로그아웃 성공")
                        }
                        val intent = Intent(context, PolaNaviActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                //탈퇴
                binding.secessionBtn.setOnClickListener{
                    UserApiClient.instance.unlink { error ->
                        if (error != null) {
                            Log.d("카카오", "탈퇴 실패")
                        }else {
                            Log.d("카카오", " 탈퇴 성공")
                            //db 데이터 삭제
                            val intent = Intent(context, PolaNaviActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        }
                    }
                }
            }
            val shared = requireActivity().getSharedPreferences("keyword", Context.MODE_PRIVATE)
            val key = shared.getBoolean("isKeyword",true)

            if(key){
                Log.d("keyword","true")
                binding.keywordToggleBtn.isChecked = true
            }else if(!key){
                Log.d("keyword","false")
                binding.keywordToggleBtn.isChecked = false
            }


            binding.keywordToggleBtn.setOnClickListener {
                val prefs = requireActivity().getSharedPreferences("keyword", Context.MODE_PRIVATE)
                val editor = prefs.edit()

                Log.i("toggleBtn", binding.keywordToggleBtn.isChecked.toString())

                if(binding.keywordToggleBtn.isChecked){
                    editor.putBoolean("isKeyword",true);
                    editor.apply()
                }else if(!binding.keywordToggleBtn.isChecked) {
                    editor.putBoolean("isKeyword", false);
                    editor.apply()
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}