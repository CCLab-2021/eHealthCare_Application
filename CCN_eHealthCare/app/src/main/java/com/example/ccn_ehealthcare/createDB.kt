package com.example.ccn_ehealthcare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ccn_ehealthcare.firebaseDB.AvailableContentsDB
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_d_b.*

class createDB : AppCompatActivity() {

    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_d_b)
        database = FirebaseDatabase.getInstance()

//        availableDoctor_btn.setOnClickListener {
//            dbforavailableDoctor()
//        }
//
//        writeReports_btn.setOnClickListener {
//            dbforwritereports()
//        }
//
//        availableServers_btn.setOnClickListener {
//            dbforavailableServer()
//
//        }

        availableContents_btn.setOnClickListener {
            dbforavailableContent()
        }

//        myReports_btn.setOnClickListener {
//            dbformyReports()
//        }
//
//        patientsInfo_btn.setOnClickListener {
//            dbforpatientsInfo()
//        }
//
//        newReports_btn.setOnClickListener {
//            dbfornewReports()
//        }
//
//        downloadFolder_btn.setOnClickListener {
//            dbfordownloadFolder()
//        }

    }

//    private fun dbfordownloadFolder() {
//
//        databaseReference = database?.reference!!.child("downloadFolder")
//
//        val folderDoctorsName = folderDoctorsName_eT.text.toString()
//        val contentNumber = (0..10).random().toString()
//        val ContentNames = folderContentNames_eT.text.toString()
//
//        val folderDoctorsNameDB = databaseReference?.child(folderDoctorsName)
//        val contentNumberDB = folderDoctorsNameDB?.child(contentNumber)
//        contentNumberDB?.setValue(downloadFolder(ContentNames))
//    }
//
//    private fun dbfornewReports() {
//
//        databaseReference = database?.reference!!.child("newReports")
//
//        val newReportsPatientsName = newReportsPatientsName_eT.text.toString()
//        val newReportsDoctorsName = newReportsDoctorsName_eT.text.toString()
//        val newReportsReport = newReportsReport_eT.text.toString()
//
//        val newReportsPatientsNameDB = databaseReference?.child(newReportsPatientsName)
//        val newReportsDoctorsNameDB = newReportsPatientsNameDB?.child(newReportsDoctorsName)
//
//        newReportsDoctorsNameDB?.setValue(newReports(newReportsReport))
//
//    }
//
//    private fun dbforpatientsInfo() {
//
//        databaseReference = database?.reference!!.child("patientsInfo")
//
//        val patientsInfoDoctorsName = patientsInfoDoctorsName_eT.text.toString()
//        val patientsInfoPatientsName = patientsInfoPatientsName_eT.text.toString()
//        val patientsInfoPatientsAge = patientsInfoPatientsAge_eT.text.toString()
//        val patientsInfoPatientsHome = patientsInfoPatientsHome_eT.text.toString()
//        val patientsInfoReport = patientsInfoReport_eT.text.toString()
//
//        val patientsInfoDoctorsNameDB = databaseReference?.child(patientsInfoDoctorsName)
//        val patientsInfoPatientsNameDB = patientsInfoDoctorsNameDB?.child(patientsInfoPatientsName)
//
//        patientsInfoPatientsNameDB?.setValue(patientsInfo(patientsInfoPatientsAge, patientsInfoPatientsHome, patientsInfoReport))
//    }
//
//    private fun dbformyReports() {
//
//        databaseReference = database?.reference!!.child("myReports")
//
//        val myReportsPatientsName = myReportsPatientsName_eT.text.toString()
//        val myReportsDoctorsName = myReportsDoctorsName_eT.text.toString()
//        val Report = Report_eT.text.toString()
//
//        val myReportsPatientsNameDB = databaseReference?.child(myReportsPatientsName)
//        val myReportsDoctorsNameDB = myReportsPatientsNameDB?.child(myReportsDoctorsName)
//        myReportsDoctorsNameDB?.setValue(myReports(Report))
//    }

    private fun dbforavailableContent() {
        val availableContentsServerNames = availableContentsServerNames_eT.text.toString()
        val contentsNumber = availableContentsNumber_eT.text.toString()
        val contentsNames = contentsNames_eT.text.toString()

        databaseReference = database?.reference!!.child("availableContents")
        val serverReference = databaseReference?.child(availableContentsServerNames)
        val contentsNumberReference = serverReference?.child(contentsNumber)

        contentsNumberReference?.setValue(AvailableContentsDB(contentsNames))
    }

//    private fun dbforwritereports() {
//        databaseReference = database?.reference!!.child("writeReports")
//
//        val writeReportsDoctorsName = writeReportDoctorsName_eT.text.toString()
//        val writeReportsPatientsName = writeReportPatientsName_eT.text.toString()
//        val existingReport = existingReports_eT.text.toString()
//        val updateReport = updateReports_eT.text.toString()
//
//        val writeReportsDoctorsNameDB = databaseReference?.child(writeReportsDoctorsName)
//        val writeReportsPatientsNameDB = writeReportsDoctorsNameDB?.child(writeReportsPatientsName)
//
//        writeReportsPatientsNameDB?.setValue(writeReports(existingReport, updateReport))
//
//    }
//
//    private fun dbforavailableDoctor() {
//        databaseReference = database?.reference!!.child("availableDoctors")
//
//        val doctorsName = doctorsName_eT.text.toString()
//        val specialty = specialty_eT.text.toString()
//
//        val doctorsNameDB = databaseReference?.child(doctorsName)
//        doctorsNameDB?.setValue(availableDoctors(specialty))
//    }
//
//    private fun dbforavailableServer() {
//        databaseReference = database?.reference!!.child("availableServers")
//
//        val serverNames = serverNames_eT.text.toString()
//        val serverIP = serverIP_eT.text.toString()
//
//
//        val serverNamesDB = databaseReference?.child(serverNames)
//        serverNamesDB?.setValue(availableServers(serverIP))
//    }

}