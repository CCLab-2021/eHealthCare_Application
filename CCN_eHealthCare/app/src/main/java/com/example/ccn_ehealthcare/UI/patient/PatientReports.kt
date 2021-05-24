package com.example.ccn_ehealthcare.UI.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.ccn_ehealthcare.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_patient_reports.*


class PatientReports : AppCompatActivity() {

    companion object {  //
        private var USERNICKNAME = "USERNICKNAME"
    }

    var userNickName = ""
    var doctorName = ""

    var databaseReference : DatabaseReference? = null   //
    var database : FirebaseDatabase? = null //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_reports)

        userNickName = intent.getStringExtra(USERNICKNAME).toString()

        database = FirebaseDatabase.getInstance()   //
        databaseReference = database?.reference!!.child("MyPatients") //


        buttonClickListener()

    }

    private fun buttonClickListener() {
        reportSearch_btn.setOnClickListener {
            val doctorText = doctorName_eT.text.toString()

            if (doctorText.trim().isNotEmpty()) {
                doctorName = doctorName_eT.text.toString()
                readMyPatientsDB(doctorName)
                linearLayout2.visibility = VISIBLE
            }
            else {
                Toast.makeText(applicationContext,"Dr Name Please..", Toast.LENGTH_SHORT).show()
                linearLayout2.visibility = INVISIBLE
            }
        }

        moveBack_btn.setOnClickListener {
            finish()
        }
    }


    private fun readMyPatientsDB(doctorName: String) {
        val doctorReference = databaseReference?.child(doctorName)
        val patientNameReference = doctorReference?.child(userNickName)

        patientNameReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val report = p0.child("report").value.toString()
                reports_tV.text = report
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occurred, Try Again!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}