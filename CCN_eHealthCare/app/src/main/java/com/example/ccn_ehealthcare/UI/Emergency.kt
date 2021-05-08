package com.example.ccn_ehealthcare.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ccn_ehealthcare.R
import kotlinx.android.synthetic.main.activity_emergency.*
import kotlinx.android.synthetic.main.activity_login.*

class Emergency : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)
        call119_btn.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW) // ACTION_CALL 적으면 바로 전화 연결
            intent.setData(Uri.parse("tel:119"))
            startActivity(intent)
        }
    }
}