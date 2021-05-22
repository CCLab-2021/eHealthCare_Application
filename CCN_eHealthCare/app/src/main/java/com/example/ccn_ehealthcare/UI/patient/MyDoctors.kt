package com.example.ccn_ehealthcare.UI.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.MyDoctorsAdapter
import com.example.ccn_ehealthcare.UI.model.MyDoctorsModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_my_doctors.*

class MyDoctors : AppCompatActivity() {

    companion object {  //
        private var USERNICKNAME = "USERNICKNAME"
    }

    var nickname = ""

    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    var doctorList = ArrayList<MyDoctorsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_doctors)

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("MyDoctors")

        nickname = intent.getStringExtra(USERNICKNAME).toString()

        buttonHandler()
        readMyDoctorsDB()
        initRecyclerView(doctorList)
    }

    private fun buttonHandler() {
        moveBack_btn.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView(doctorList: ArrayList<MyDoctorsModel>) {
        myDoctors_rV.layoutManager = LinearLayoutManager(this)
        myDoctors_rV.setHasFixedSize(true)
        myDoctors_rV.adapter = MyDoctorsAdapter(doctorList)
    }

    private fun readMyDoctorsDB() {
        val patientsReference = databaseReference?.child(nickname)

        patientsReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    val doctorName = snapshot.key.toString()
                    val specialty = snapshot.child("specialty").value.toString()

                    val name = "Name : $doctorName"
                    val designation = "Specialty : $specialty"

                    doctorList.add(MyDoctorsModel(name, designation))
                }

                initRecyclerView(doctorList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occurred, Try Again!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}