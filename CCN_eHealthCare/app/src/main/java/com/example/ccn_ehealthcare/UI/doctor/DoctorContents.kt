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
import com.example.ccn_ehealthcare.UI.adapter.HospitalAdapter
import com.example.ccn_ehealthcare.UI.model.HospitalModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_doctor_contents.*
import kotlinx.android.synthetic.main.activity_doctor_contents.moveBack_btn

class DoctorContents : AppCompatActivity() {

    val STORAGE_PERMISSOIN_CODE: Int = 1000
    var url = ""


    var databaseReference : DatabaseReference? = null   //
    var database : FirebaseDatabase? = null //

    val contentsList = ArrayList<HospitalModel>()
    private val HOSPITAL="HOSPITAL"
    var hospitalname= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_contents)
        hospitalname = intent.getStringExtra(HOSPITAL).toString()


        database = FirebaseDatabase.getInstance()   //
        databaseReference = database?.reference!!.child("availableContents") //


        val contentsList = ArrayList<HospitalModel>()

        buttonHandler()
        readHospitalcontentDB()    //
        initRecyclerView(contentsList)    //
    }
    private fun checkVersion(url : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSOIN_CODE)
            }
            else {
                startDownloading(url)
            }
        }
        else {
            startDownloading(url)
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

    private fun initRecyclerView(contentsList: java.util.ArrayList<HospitalModel>) {
        var adapter = HospitalAdapter(contentsList)

        hospitalcontents_rV.layoutManager = LinearLayoutManager(this)
        hospitalcontents_rV.setHasFixedSize(true)
        hospitalcontents_rV.adapter = adapter
        Log.e("url", url)

        adapter.setOnItemClickListener(object : HospitalAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: HospitalModel, pos : Int) {
                Log.e("url확인", data.url)
                checkVersion(data.url)
            }

        })
    }

    private fun readHospitalcontentDB() {
        val hospitalcontentReference = databaseReference?.child(hospitalname)

        hospitalcontentReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    val contentsName= snapshot.child("contentNames").value.toString()

                    url= snapshot.child("contentURL").value.toString()
                    contentsList.add(HospitalModel(contentsName, url))
                }

                initRecyclerView(contentsList)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occurred, Try Again!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}