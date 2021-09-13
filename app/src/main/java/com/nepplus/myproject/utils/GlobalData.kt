package com.nepplus.myproject.utils

import com.nepplus.myproject.datas.UserData

class GlobalData {

    companion object {
//        로그인한 사람은 없을수ㅗ 있다 => null로 로그인한 사람이 없다
//        UserData? 로 NULL 허용
//        기본값 : 로그인한 사람이 없다. null 미리 대입

        var loginUser : UserData? = null
    }
}