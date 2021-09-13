package com.nepplus.myproject.datas

import android.os.Message

//서버가 주는 기본 형태의 응답을 담는 클래스 ( 파싱결과로 활용)

class BasicResponse(
    var code : Int,
    var message: String,
    var data : DataResponse

    ) {
}