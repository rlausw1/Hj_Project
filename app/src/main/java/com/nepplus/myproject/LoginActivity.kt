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

import com.facebook.login.widget.LoginButton




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




        // Callback registration
        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code

                Log.d("확인용", loginResult.toString())
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code


            }
        })


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