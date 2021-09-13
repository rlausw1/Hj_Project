package com.nepplus.myproject

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.myproject.databinding.ActivityLoginBinding

class LiginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()






    }

    override fun setupEvents() {

    }

    override fun setValues() {
    }
}