package com.nepplus.myproject

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "e4c4318472b8b7fc8cd73f765de11cf0")
    }
}