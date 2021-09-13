package com.nepplus.myproject.datas

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

class UserData(
    var id : Int,
    var provider : String,
    @SerializedName("nick_name")
    var nickName: String,
    var email : String) {



}