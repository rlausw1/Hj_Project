package com.nepplus.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nepplus.myproject.datas.AppointmentData

class ViewMapActivity : BaseActivity() {

    lateinit var mAppointmentData: AppointmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_map)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {

        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

    }
}