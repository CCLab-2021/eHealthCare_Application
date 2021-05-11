package com.example.ccn_ehealthcare.UI.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.hospitalAdapter
import com.example.ccn_ehealthcare.UI.adapter.myPatientsAdapter
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import com.example.ccn_ehealthcare.UI.model.myPatientsModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor_contents.*
import kotlinx.android.synthetic.main.activity_my_patients.*
import kotlinx.android.synthetic.main.doctor_reports_layout.*

class MyPatients : AppCompatActivity() {
    var databaseReference : DatabaseReference? = null   //
    var database : FirebaseDatabase? = null //

    val patientsList = ArrayList<myPatientsModel>()

    companion object {  //
        private var USERNICKNAME = "USERNICKNAME"
    }

    var userNickName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_patients)
        userNickName = intent.getStringExtra(USERNICKNAME).toString()
        Log.e("CHECK2", userNickName)

        database = FirebaseDatabase.getInstance()   //
        databaseReference = database?.reference!!.child("MyPatients") //


        val patientsList = ArrayList<myPatientsModel>()

        readmypatienscontentDB()    //
        initRecyclerView(patientsList)    //

        val report = intent.getSerializableExtra("report")
        Log.e("TEST", report.toString())
    }

    private fun initRecyclerView(patientsList: java.util.ArrayList<myPatientsModel>) {
        var adapter = myPatientsAdapter(patientsList)

        myPatients_rV.layoutManager = LinearLayoutManager(this)
        myPatients_rV.setHasFixedSize(true)
        myPatients_rV.adapter = adapter

        adapter.setOnItemClickListener(object : myPatientsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(applicationContext, "CLICK!", Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun readmypatienscontentDB() {
        Log.e("READDB", "hospitala db읽기")
        val mypatientscontentReference = databaseReference?.child(userNickName)

        mypatientscontentReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//                patientList.clear()
                Log.e("DATACHANGE", "AAAAAAA")
                Log.e("dd", p0.key.toString())

                for (snapshot in p0.children) {
                    Log.e("확인1", snapshot.toString())
                    val patientName = snapshot.key.toString()
                    val patientAddress = snapshot.child("patientsAddress").value.toString()
                    val patientAge = snapshot.child("patientsAge").value.toString()
                    val patientReport = snapshot.child("report").value.toString()

                    val age = "Age : $patientAge"
                    val add = "Address : $patientAddress"

                    patientsList.add(myPatientsModel(patientName, add, age, patientReport))
                }

                initRecyclerView(patientsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ONCANCEL", error.message)
            }
        })
    }
}