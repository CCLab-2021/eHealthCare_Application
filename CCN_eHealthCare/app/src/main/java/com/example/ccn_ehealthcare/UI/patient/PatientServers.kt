package com.example.ccn_ehealthcare.UI.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ccn_ehealthcare.R
import kotlinx.android.synthetic.main.activity_patient_servers.*

class PatientServers : AppCompatActivity() {
    private val SERVERNAME = "SERVERNAME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_servers)

        buttonHandler()
    }

    private fun buttonHandler() {
        server1_btn.setOnClickListener {
            startActivity(Intent(this, PatientContents::class.java).apply {
                putExtra(SERVERNAME, "Server1")
            })
        }
        server2_btn.setOnClickListener {
            startActivity(Intent(this, PatientContents::class.java).apply {
                putExtra(SERVERNAME, "Server2")
            })
        }
        server3_btn.setOnClickListener {
            startActivity(Intent(this, PatientContents::class.java).apply {
                putExtra(SERVERNAME, "Server2")
            })
        }

        moveBack_btn.setOnClickListener {
            finish()
        }
    }
}