package com.example.ccn_ehealthcare.UI.doctor

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.adapter.hospitalAdapter
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor_contents.*
import kotlinx.android.synthetic.main.activity_doctor_contents.moveBack_btn

class DoctorContents : AppCompatActivity() {

    val STORAGE_PERMISSOIN_CODE: Int = 1000
    var url = ""


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

        buttonHandler()
        readHospitalcontentDB()    //
        initRecyclerView(contentsList)    //
    }
    private fun checkVersion(url : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSOIN_CODE)
                Log.e("성공1", "AAAAAAA")

            }
            else {
                Log.e("성공2", url)
                startDownloading(url)


            }
        }
        else {
            startDownloading(url)
            Log.e("성공3", "AAAAAAA")
        }
    }

    private fun startDownloading(url:String) {

        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setTitle("Download")
                .setDescription("The file is downloading..")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")
                .allowScanningByMediaScanner()

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            STORAGE_PERMISSOIN_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloading(url)
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun buttonHandler() {
        moveBack_btn.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView(contentsList: java.util.ArrayList<hospitalModel>) {
        var adapter = hospitalAdapter(contentsList)

        hospitalcontents_rV.layoutManager = LinearLayoutManager(this)
        hospitalcontents_rV.setHasFixedSize(true)
        hospitalcontents_rV.adapter = adapter
        Log.e("url", url)

        adapter.setOnItemClickListener(object : hospitalAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: hospitalModel, pos : Int) {
                Log.e("url확인", data.url)
                checkVersion(data.url)
            }

        })
//        adapter.setOnItemClickListener(contentsList: contents) {
//            override fun onItemClick(position: Int) {
////                Log.e("뭐ㅑㅇ", url)
////                readHospitalcontentDB()
//                checkVersion(this.url)
////                Toast.makeText(applicationContext, "CLICK!", Toast.LENGTH_SHORT).show()
//            }
//        })

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

                    var contentsName= snapshot.child("contentNames").value.toString()
                    url= snapshot.child("contentURL").value.toString()
                    Log.e("PATIENTNAME", url)
                    contentsList.add(hospitalModel(contentsName, url))
                }

                initRecyclerView(contentsList)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ONCANCEL", error.message)
            }
        })
    }
}