package com.nepplus.myproject.datas

import com.google.gson.annotations.SerializedName

class AppointmentData(
    var id : Int,
    @SerializedName("user_id")
    var userId : Int,
    var title : String,

    ) {
}