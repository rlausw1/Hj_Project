package com.nepplus.myproject

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.facebook.CallbackManager
import com.nepplus.myproject.databinding.ActivityLoginBinding
import java.security.MessageDigest
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback

import android.view.View
import com.facebook.AccessToken
import com.facebook.login.LoginManager

import com.facebook.login.widget.LoginButton
import java.util.*


class LoginActivity : BaseActivity() {
    
    lateinit var callbackManager : CallbackManager

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()






    }

    override fun setupEvents() {

        callbackManager = CallbackManager.Factory.create();
        binding.loginButton.setReadPermissions("email")
        binding.facebookLoginBtn.setOnClickListener {
//             우리가 붙인 버튼에 기능 활용
//            커스터 버튼에 로그인 하고 돌아온 callback을 따로 설정

            LoginManager.getInstance().registerCallback(callbackManager, object  : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    Log.d("로그인 성공", "우리가 만든 버튼으로 성공")
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException?) {
                }


            })

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
        }




        // Callback registration
//        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//            override fun onSuccess(loginResult: LoginResult?) {
//                // App code
//
//                Log.d("확인용", loginResult.toString())
//
//                val accessToken = AccessToken.getCurrentAccessToken()
//                Log.d("페북토큰", accessToken.toString())
//            }
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException) {
//                // App code
//
//
//            }
//        })


    }

    override fun setValues() {

        val info = packageManager.getPackageInfo(
        "com.nepplus.myproject",
        PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}