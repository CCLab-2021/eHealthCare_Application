package com.example.ccn_ehealthcare.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.callDocAdapter
import com.example.ccn_ehealthcare.UI.adapter.hospitalAdapter
import com.example.ccn_ehealthcare.UI.model.MyDoctorsModel
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import com.example.ccn_ehealthcare.UI.patient.MyDoctors
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_emergency.*
import kotlinx.android.synthetic.main.activity_emergency.myDoctors_rV
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_my_doctors.*


class Emergency : AppCompatActivity() {

    companion object {  //
        private var USERNICKNAME = "USERNICKNAME"
    }

    var nickname = ""

    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    var doctorList = ArrayList<MyDoctorsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)
        call119_btn.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW) // ACTION_CALL 적으면 바로 전화 연결
            intent.setData(Uri.parse("tel:119"))
            startActivity(intent)
        }
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("MyDoctors")

        nickname = intent.getStringExtra(USERNICKNAME).toString()
//
//        buttonHandler()
        readMyDoctorsDB()
        initRecyclerView(doctorList)
    }
//    private fun buttonHandler() {
//        moveBack_btn.setOnClickListener {
//            finish()
//        }
//    }

    private fun initRecyclerView(doctorList: java.util.ArrayList<MyDoctorsModel>) {
        var adapter = callDocAdapter(doctorList)
        myDoctors_rV.layoutManager = LinearLayoutManager(this)
        myDoctors_rV.setHasFixedSize(true)
        myDoctors_rV.adapter = callDocAdapter(doctorList)
        adapter.setOnItemClickListener(object : callDocAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: MyDoctorsModel, pos : Int) {
                Log.e("전화번호!!!!!!!!!!!!!!!!", data.phoneNum)
                val num = "tel:"+data.phoneNum
                var intent = Intent(Intent.ACTION_VIEW) // ACTION_CALL 적으면 바로 전화 연결
                intent.setData(Uri.parse(num))
                startActivity(intent)
            }

        })

    }

    private fun readMyDoctorsDB() {
        val patientsReference = databaseReference?.child(nickname)

        patientsReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    Log.e("SNAPSHOT", snapshot.key.toString())
                    var doctorName = snapshot.key.toString()
                    var doctorNum = snapshot.child("phoneNum").value.toString()
                    Log.e("SNAPSHOT", doctorNum)


                    doctorList.add(MyDoctorsModel(doctorName, doctorNum))
                }

                initRecyclerView(doctorList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ONCANCEL", error.message)
            }

        })
    }
}