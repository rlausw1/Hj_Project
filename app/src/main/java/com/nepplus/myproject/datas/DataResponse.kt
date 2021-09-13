package com.nepplus.myproject.datas

import android.service.autofill.UserData

class DataResponse(
//    로그인 성공시 파싱용 변수

    var token: String,
    var user: com.nepplus.myproject.datas.UserData
//    이 밑으로는 약속 목록파싱용 변수
    var appointments : List<AppointmentData>

) {


}