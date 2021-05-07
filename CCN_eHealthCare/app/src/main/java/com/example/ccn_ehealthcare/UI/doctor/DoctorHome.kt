package com.example.ccn_ehealthcare.UI.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.UserProfile
import com.example.ccn_ehealthcare.UI.auth.Login
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_doctor_home.*

class DoctorHome : AppCompatActivity() {
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
        setContentView(R.layout.activity_doctor_home)

        userUid = intent.getStringExtra(USERUID).toString()
        userNickName = intent.getStringExtra(USERNICKNAME).toString()
        userFullName = intent.getStringExtra(USERFULLNAME).toString()
        userEmail = intent.getStringExtra(USEREMAIL).toString()
        userPW = intent.getStringExtra(USERPW).toString()
        userBirth = intent.getStringExtra(USERBIRTH).toString()
        userType = intent.getStringExtra(USERTYPE).toString()

        Log.e("USERINFO", "uid : ${userUid}, nickname : ${userNickName}, password : ${userPW}, birth : ${userBirth}")

        welcome_tV.text = "Hello $userNickName. Have a nice day."

        buttonHandler()
    }

    private fun buttonHandler() {
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

        dPatients_btn.setOnClickListener {
            startActivity(Intent(this, MyPatients::class.java).apply {
                putExtra(USERNICKNAME, userNickName)
            })
        }

        dReports_btn.setOnClickListener {
            startActivity(Intent(this, DoctorReports::class.java).apply {
                putExtra(USERNICKNAME, userNickName)
            })
        }

        download_btn.setOnClickListener {
            startActivity(Intent(this, DoctorServers::class.java))
        }

        dLogout_btn.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(applicationContext, "Successfully Logout", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}