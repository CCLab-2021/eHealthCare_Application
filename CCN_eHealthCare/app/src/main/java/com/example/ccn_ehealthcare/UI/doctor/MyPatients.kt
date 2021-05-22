package com.example.ccn_ehealthcare.UI.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.MyPatientsAdapter
import com.example.ccn_ehealthcare.UI.model.MyPatientsModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_my_patients.*
import kotlinx.android.synthetic.main.doctor_reports_layout.view.*

class MyPatients : AppCompatActivity() {
    var databaseReference : DatabaseReference? = null   //
    var database : FirebaseDatabase? = null //

    val patientsList = ArrayList<MyPatientsModel>()

    companion object {  //
        private var USERNICKNAME = "USERNICKNAME"
    }

    var userNickName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_patients)
        userNickName = intent.getStringExtra(USERNICKNAME).toString()

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("MyPatients") //


        val patientsList = ArrayList<MyPatientsModel>()

        readmypatienscontentDB()
    }

    private fun initRecyclerView(patientsList: ArrayList<MyPatientsModel>) {
        val adapter = MyPatientsAdapter(patientsList)

        myPatients_rV.layoutManager = LinearLayoutManager(this)
        myPatients_rV.setHasFixedSize(true)
        myPatients_rV.adapter = adapter

        adapter.setOnItemClickListener(object : MyPatientsAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: MyPatientsModel, pos : Int) {
                val report = v.patientReport_eT .text.toString()

                val myPatientsContentReference = databaseReference?.child(userNickName)
                val a = myPatientsContentReference?.child(data.patientsName)
                a?.child("report")!!.setValue(report)
                patientsList.clear()
                adapter.notifyDataSetChanged()
            }

        })


    }

    private fun readmypatienscontentDB() {
        val mypatientscontentReference = databaseReference?.child(userNickName)

        mypatientscontentReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    val patientName = snapshot.key.toString()
                    val patientAddress = snapshot.child("patientsAddress").value.toString()
                    val patientAge = snapshot.child("patientsAge").value.toString()
                    val patientReport = snapshot.child("report").value.toString()

                    val age = "Age : $patientAge"
                    val add = "Address : $patientAddress"

                    patientsList.add(MyPatientsModel(patientName, add, age, patientReport))
                }

                initRecyclerView(patientsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occurred, Try Again!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}