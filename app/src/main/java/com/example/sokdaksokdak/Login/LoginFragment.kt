package com.example.sokdaksokdak.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sokdaksokdak.PolaNaviActivity


import com.example.sokdaksokdak.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {
    private lateinit var kakaoSocial: KakaoSocial
    private lateinit var googleSocial: GoogleSocial

    private lateinit var binding: FragmentLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val GOOGLE_REQUEST_CODE = 99
    val TAG = "googleLogin"
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.kakaoLoginButton.setOnClickListener{
            kakaoSocial = KakaoSocial()
            val user = UserLogin(kakaoSocial)
            user.login(context)
        }

        binding.googleLoginButton.setOnClickListener {
            googleSocial = GoogleSocial()
            val user = UserLogin(googleSocial)
            user.login(context)

            googleSignInClient = googleSocial.getClient()
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
                Log.i("google login",account.givenName + "구글 로그인 성공")
            } catch (e: ApiException) {
//                Toast.makeText(this, "구글 회원가입에 실패하였습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.i("google login","구글 로그인 실패")
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                toMainActivity(auth.currentUser)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        //유저가 로그인되어있는지 확인
        val currentUser = auth.currentUser
        toMainActivity(currentUser)
    }

    //유저가 카카오 로그인하면 메인액티비티로 이동
    private fun moveMain() {
        startActivity(Intent(context, PolaNaviActivity::class.java))
    }

    private fun toMainActivity(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(context, PolaNaviActivity::class.java))
        }
    }

}




