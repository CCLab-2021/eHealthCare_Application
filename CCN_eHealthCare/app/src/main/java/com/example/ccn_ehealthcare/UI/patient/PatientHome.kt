package com.example.ccn_ehealthcare.UI.patient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.Emergency
import com.example.ccn_ehealthcare.UI.UserProfile
import com.example.ccn_ehealthcare.UI.auth.Login
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_patient_home.*

class PatientHome : AppCompatActivity() {

    companion object {
        private var USERUID = "USERUID"
        private var USERNICKNAME = "USERNICKNAME"
        private var USERFULLNAME = "USERFULLNAME"
        private var USEREMAIL = "USEREMAIL"
        private var USERPW = "USERPW"
        private var USERBIRTH = "USERBIRTH"
        private var USERTYPE = "USERTYPE"
    }

    var userUid = ""
    var userNickName = ""
    var userFullName = ""
    var userEmail = ""
    var userPW = ""
    var userBirth = ""
    var userType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_home)

        userUid = intent.getStringExtra(USERUID).toString()
        userNickName = intent.getStringExtra(USERNICKNAME).toString()
        userFullName = intent.getStringExtra(USERFULLNAME).toString()
        userEmail = intent.getStringExtra(USEREMAIL).toString()
        userPW = intent.getStringExtra(USERPW).toString()
        userBirth = intent.getStringExtra(USERBIRTH).toString()
        userType = intent.getStringExtra(USERTYPE).toString()

        Log.e("USERINFO", "uid : ${userUid}, nickname : ${userNickName}, password : ${userPW}, birth : ${userBirth}")

        welcome_tV.text = "Welcome ${userNickName}. How are you doing today?"

        btnClickListener()
    }

    private fun btnClickListener() {
        pUpdateProfile_btn.setOnClickListener {
            startActivity(Intent(this, UserProfile::class.java).apply {
                putExtra(USERUID, userUid)
                putExtra(USERNICKNAME, userNickName)
                putExtra(USERFULLNAME, userFullName)
                putExtra(USEREMAIL, userEmail)
                putExtra(USERPW, userPW)
                putExtra(USERBIRTH, userBirth)
                putExtra(USERTYPE, userType)
            })
        }

        pDoctors_btn.setOnClickListener {
            startActivity(Intent(this, MyDoctors::class.java).apply {
                putExtra(USERNICKNAME, userNickName)
            })
        }

        pReports_btn.setOnClickListener {
            startActivity(Intent(this, PatientReports::class.java).apply {
                putExtra(USERNICKNAME, userNickName)
            })
        }

        pDownload_btn.setOnClickListener {
            startActivity(Intent(this, PatientServers::class.java).apply {
                putExtra(USERNICKNAME, userNickName)
            })
        }

        pEmergency_btn.setOnClickListener {
            startActivity(Intent(this, Emergency::class.java))
        }

        pLogout_btn.setOnClickListener {
            logout()
        }


    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Successfully Logout", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}