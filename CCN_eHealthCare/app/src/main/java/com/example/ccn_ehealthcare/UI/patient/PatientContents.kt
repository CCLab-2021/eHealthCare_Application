package com.example.ccn_ehealthcare.UI.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.ServerAdapter
import com.example.ccn_ehealthcare.UI.model.ServerModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_patient_contents.*

class PatientContents : AppCompatActivity() {

    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    val contentsList = ArrayList<ServerModel>()
    private val SERVERNAME = "SERVERNAME"
    var serverName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_contents)

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("availableContents")

        serverName = intent.getStringExtra(SERVERNAME).toString()

        val contentsList = ArrayList<ServerModel>()

        buttonHandler()
        readServerContentDB()
        initRecyclerView(contentsList)
    }

    private fun buttonHandler() {
        moveBack_btn.setOnClickListener {
            finish()
        }
    }

    private fun readServerContentDB() {
        val serverContentReference = databaseReference?.child(serverName)

        serverContentReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    val contentsName = snapshot.child("contentNames").value.toString()

                    contentsList.add(ServerModel(contentsName))
                }

                initRecyclerView(contentsList)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occurred, Try Again!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initRecyclerView(contentList: ArrayList<ServerModel>) {
        serverContents_rV.layoutManager = LinearLayoutManager(this)
        serverContents_rV.setHasFixedSize(true)
        serverContents_rV.adapter = ServerAdapter(contentList)

    }
}