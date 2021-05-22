package com.example.ccn_ehealthcare.UI.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ccn_ehealthcare.R
import kotlinx.android.synthetic.main.activity_doctor_servers.*

class DoctorServers : AppCompatActivity() {
    private val HOSPITAL="HOSPITAL"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_servers)

        buttonHandler()

    }

    private fun buttonHandler() {
        server1_btn.setOnClickListener {
            val intent = Intent(this, DoctorContents::class.java).apply {
                putExtra(HOSPITAL,"HospitalA")
            }
            startActivity(intent)
        }

        server2_btn.setOnClickListener {
            val intent = Intent(this, DoctorContents::class.java).apply {
                putExtra(HOSPITAL,"HospitalB")
            }
            startActivity(intent)
        }

        server3_btn.setOnClickListener {
            val intent = Intent(this, DoctorContents::class.java).apply {
                putExtra(HOSPITAL,"HospitalC")
            }
            startActivity(intent)
        }

        moveBack_btn.setOnClickListener {
            finish()
        }
    }
}