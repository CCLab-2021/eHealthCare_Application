package com.example.ccn_ehealthcare.UI.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.hospitalAdapter
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor_contents.*
import kotlinx.android.synthetic.main.activity_patient_reports.*

class DoctorContents : AppCompatActivity() {

    var databaseReference : DatabaseReference? = null   //
    var database : FirebaseDatabase? = null //

    val contentsList = ArrayList<hospitalModel>()
    private val HOSPITAL="HOSPITAL"
    var hospitalname= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_contents)
        hospitalname = intent.getStringExtra(HOSPITAL).toString()
        Log.e("병원이름", hospitalname)

        database = FirebaseDatabase.getInstance()   //
        databaseReference = database?.reference!!.child("availableContents") //


        val contentsList = ArrayList<hospitalModel>()

        readHospitalcontentDB()    //
        initRecyclerView(contentsList)    //
    }
    private fun initRecyclerView(reportsList: java.util.ArrayList<hospitalModel>) {

        hospitalcontents_rV.layoutManager = LinearLayoutManager(this)
        hospitalcontents_rV.setHasFixedSize(true)
        hospitalcontents_rV.adapter = hospitalAdapter(contentsList)

    }

    private fun readHospitalcontentDB() {
            Log.e("READDB", "hospitala db읽기")
            val hospitalcontentReference = databaseReference?.child(hospitalname)

        hospitalcontentReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
//                patientList.clear()
                Log.e("DATACHANGE", "AAAAAAA")
                Log.e("dd", p0.key.toString())

                for (snapshot in p0.children) {
                    Log.e("READ", snapshot.toString())
                    Log.e("PATIENTNAME", snapshot.key.toString())
                    //Log.e("doctorsName", snapshot.child("doctorsName").value.toString())
                    Log.e("report", snapshot.child("report").value.toString())
//                    Log.e("PATIENTREPORT", snapshot.child("report").value.toString())

                    var contentsName= snapshot.child("contentNames").value.toString()
                    //var doctorsName = snapshot.child("doctorsName").value.toString()
//                    var report = snapshot.child("report").value.toString()

                    contentsList.add(hospitalModel(contentsName))
                }

                initRecyclerView(contentsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ONCANCEL", error.message)
            }
        })
    }
}