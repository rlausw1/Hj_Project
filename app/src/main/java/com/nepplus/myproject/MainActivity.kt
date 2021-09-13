package com.nepplus.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.myproject.databinding.ActivityMainBinding
import com.nepplus.myproject.utils.GlobalData

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.addAppoinmentBtn.setOnClickListener {
            val myIntent = Intent(mContext, EditAppoinmentActivity::class.java)
            startActivity(myIntent)
        }




    }

    override fun setValues() {

        Toast.makeText(mContext, "${GlobalData.loginUser!!.nickName}님 환영합니다!", Toast.LENGTH_SHORT).show()
    }
}