package com.example.ccn_ehealthcare.UI.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        Log.e("READDB", "START")
        val serverContentReference = databaseReference?.child(serverName)

        serverContentReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                Log.e("DATACHANGE", "START")
                Log.e("SERVERNAME", p0.key.toString())

                for (snapshot in p0.children) {
                    Log.e("ContentNum", snapshot.key.toString())
                    Log.e("ContentName", snapshot.child("contentNames").value.toString())

                    val contentsName = snapshot.child("contentNames").value.toString()

                    contentsList.add(ServerModel(contentsName))
                }

                initRecyclerView(contentsList)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ONCANCLE", error.message)
            }

        })
    }

    private fun initRecyclerView(contentList: ArrayList<ServerModel>) {
        serverContents_rV.layoutManager = LinearLayoutManager(this)
        serverContents_rV.setHasFixedSize(true)
        serverContents_rV.adapter = ServerAdapter(contentList)

    }
}