package com.nepplus.myproject.web

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

    companion object {

//        서버 주소
        private val hostURL = "http://3.36.146.152"

//        Retrofit 형태의 변수가 => OkHttpClient처럼 실체 호출 담당
//        레트로핏 객체는 하나만 만들어주고 여러화면에서 공유해서 사용
//        객체를 하나로 유지하자 => SingleTon 패턴 사용

        private val retrofit : Retrofit? = null
        fun getRetrofit() : Retrofit {

            if (retrofit == null ) {
                retrofit = Retrofit.Builder()
                    .baseUrl(hostURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!

        }


    }
}