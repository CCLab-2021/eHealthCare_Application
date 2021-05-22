package com.example.ccn_ehealthcare.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.CallDoctorAdapter
import com.example.ccn_ehealthcare.UI.model.CallDoctorModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_emergency.*
import kotlinx.android.synthetic.main.activity_emergency.myDoctors_rV


class Emergency : AppCompatActivity() {

    companion object {  //
        private var USERNICKNAME = "USERNICKNAME"
    }

    var nickname = ""

    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    var doctorList = ArrayList<CallDoctorModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("MyDoctors")

        nickname = intent.getStringExtra(USERNICKNAME).toString()

        buttonHandler()
        readMyDoctorsDB()
        initRecyclerView(doctorList)
    }

    private fun buttonHandler() {
        call119_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW) // ACTION_CALL 적으면 바로 전화 연결
            intent.setData(Uri.parse("tel:119"))
            startActivity(intent)
        }

        moveBack_btn.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView(doctorList: java.util.ArrayList<CallDoctorModel>) {
        myDoctors_rV.layoutManager = LinearLayoutManager(this)
        myDoctors_rV.setHasFixedSize(true)
        myDoctors_rV.adapter = CallDoctorAdapter(doctorList)
    }

    private fun readMyDoctorsDB() {
        val patientsReference = databaseReference?.child(nickname)

        patientsReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    val doctorName = snapshot.key.toString()
                    val phoneNum = snapshot.child("phoneNum").value.toString()

                    doctorList.add(CallDoctorModel(doctorName, phoneNum))
                }

                initRecyclerView(doctorList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error occurred!, Try Again!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}