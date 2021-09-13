package com.nepplus.myproject

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.nepplus.myproject.databinding.ActivityLoginBinding
import java.security.MessageDigest

import com.facebook.login.LoginResult

import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager

import com.facebook.login.widget.LoginButton
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import com.nepplus.myproject.datas.BasicResponse
import com.nepplus.myproject.utils.ContextUtil
import com.nepplus.myproject.utils.GlobalData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class LoginActivity : BaseActivity() {

    lateinit var callbackManager: CallbackManager

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()


    }

    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            val inputId = binding.emailEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()
//            POST -> /user로 로그인 시도
            apiService.postRequestLogin(inputId, inputPw).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        Toast.makeText(mContext, basicResponse.message, Toast.LENGTH_SHORT).show()

                        Log.d("토큰", basicResponse.data.token)
                        ContextUtil.setToken(mContext, basicResponse.data.token)

//                        Toast.makeText(mContext, basicResponse.data.user.email, Toast.LENGTH_SHORT).show()
//로그인한 사람이 누구인지 => GlobalData클래스에 저장

                        GlobalData.loginUser = basicResponse.data.user


                    } else {
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)
                        Log.d("응답본문", jsonObj.toString())
                        val message = jsonObj.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()


                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }

            })
        }


        binding.signUpBtn.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        binding.kakaoLoginBtn.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->
                if (error != null) {
                    Log.e("카카오로그인", "로그인 실패", error)
                } else if (token != null) {
                    Log.i("카카오로그인", "로그인 성공 ${token.accessToken}")

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("카카오로그인", "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.i(
                                "카카오로그인", "사용자 정보 요청 성공" +
                                        "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                            )

//                           소셜로그인 api에 "kakao"로 id / 닉네임 전송
                            apiService.postRequestSocialLogin("kakao",
                                user.id.toString(),
                                user.kakaoAccount?.profile?.nickname!!).enqueue(object  : Callback<BasicResponse> {
                                override fun onResponse(
                                    call: Call<BasicResponse>,
                                    response: Response<BasicResponse>
                                ) {
                                    val basicResponse = response.body()!!
                                    ContextUtil.setToken(mContext, basicResponse.data.token)
                                    GlobalData.loginUser = basicResponse.data.user

                                }

                                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                                }
                                })

                        }
                    }


                }
            }

        }

        callbackManager = CallbackManager.Factory.create();
        binding.loginButton.setReadPermissions("email")
        binding.facebookLoginBtn.setOnClickListener {
//             우리가 붙인 버튼에 기능 활용
//            커스터 버튼에 로그인 하고 돌아온 callback을 따로 설정

            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        Log.d("로그인 성공", "우리가 만든 버튼으로 성공")
//                    페이스북에 접근할수 있는 토큰이 생겼다
//                    로그인한 사람 정보를 받아오는데 활용
                        val graphRequest = GraphRequest.newMeRequest(
                            result?.accessToken,
                            object : GraphRequest.GraphJSONObjectCallback {
                                override fun onCompleted(
                                    jsonObj: JSONObject?,
                                    response: GraphResponse?
                                ) {

                                    Log.d("내정보내용", jsonObj.toString())
                                    val name = jsonObj!!.getString("name")
                                    val id = jsonObj.getString("id")

//                           가입한 회원 이름 => 우리 서버에 사용자 이름으로 (닉네임) 저장
                                    Log.d("이름", name)
//                            페북에서 사용자를 구별하는 고유번호 => 우리 서버에 같이 저장. 회원가입 또는 로그인 근거자료로 활용용
                                    Log.d("id값", id)


                                    apiService.postRequestSocialLogin("facebook", id, name)
                                        .enqueue(object : Callback<BasicResponse> {
                                            override fun onResponse(
                                                call: Call<BasicResponse>,
                                                response: Response<BasicResponse>
                                            ) {
                                                val basicResponse = response.body()!!
                                                Toast.makeText(
                                                    mContext,
                                                    basicResponse.message,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                Log.d("API 서버가 준 토큰값", basicResponse.data.token)

//                                    ContextUtil 등으로 SharedPreferences로 토큰값 저장.
                                                ContextUtil.setToken(mContext, basicResponse.data.token)
                                                GlobalData.loginUser = basicResponse.data.user

//                                    메인화면으로 이동.
                                                val myIntent = Intent(mContext, MainActivity::class.java)
                                                startActivity(myIntent)
                                                finish()

                                            }

                                            override fun onFailure(call: Call<BasicResponse>,t: Throwable
                                            ) {
                                            }


                                        })

                                }

                            })
//                    위에서 정리한 내용을 들고, 내 정보를 실제로 요청
                        graphRequest.executeAsync()

                    }

                    override fun onCancel() {
                    }

                    override fun onError(error: FacebookException?) {
                    }


                })

            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile"))
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