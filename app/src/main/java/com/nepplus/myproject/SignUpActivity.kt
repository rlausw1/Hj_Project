package com.nepplus.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.myproject.databinding.ActivitySignUpBinding
import com.nepplus.myproject.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {
        binding.signUpBtn.setOnClickListener {

            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.pwEdt.text.toString()
            val inputNick = binding.nicknameEdt.text.toString()

            apiService.putRequestSignUp(inputEmail, inputPw, inputNick).enqueue(object  : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

//                    response.body -> 응답 코드 200번 이어야 들어있다.
//                    가입 실패 / 로그인 실패 -> 응답 코드 400 -> errorBody 에서 따로 찾아야함(실패)

                     if (response.isSuccessful) {
                         val basicResponse = response.body()!!

                         Log.d("서버 메세지", basicResponse.message)
                         Toast.makeText(mContext, basicResponse.message, Toast.LENGTH_SHORT).show()


                     }
                    else {
                        val erroeBodyStr = response.errorBody()!!.string()

//                         단순 JSON 형태의 Sting으로 내려옴 => JSONObject 형태로 가공 필요
                         Log.d("에러경우", erroeBodyStr)
                         val jsonObj = JSONObject(erroeBodyStr)
                         val message = jsonObj.getString("message")
//                         runOnUiThread를 해주지 않아도 ui 접근 가능
                         Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                     }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }


            })


        }
    }

    override fun setValues() {
    }
}