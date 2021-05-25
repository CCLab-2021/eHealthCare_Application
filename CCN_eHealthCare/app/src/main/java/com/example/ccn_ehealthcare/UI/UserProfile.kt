package com.example.ccn_ehealthcare.UI

import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ccn_ehealthcare.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_profile.*


class UserProfile : AppCompatActivity() {

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

    lateinit var firebaseAuth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        firebaseAuth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("User")


        userUid = intent.getStringExtra(USERUID).toString()
        userNickName = intent.getStringExtra(USERNICKNAME).toString()
        userEmail = intent.getStringExtra(USEREMAIL).toString()
        userPW = intent.getStringExtra(USERPW).toString()
        userType = intent.getStringExtra(USERTYPE).toString()
        userFullName = intent.getStringExtra(USERFULLNAME).toString()
        userBirth = intent.getStringExtra(USERBIRTH).toString()

        buttonHandler()
    }

    private fun buttonHandler() {
        changePW_btn.setOnClickListener {
            changePassword()
        }

        profileSave_btn.setOnClickListener {
            updateUserInfo()
        }

        profileBack_btn.setOnClickListener {
            finish()
        }

    }

    private fun changePassword() {
        reAuthenticate(userEmail, userPW)
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth?.currentUser
        val previousPW = previousPW_eT.text.toString()
        val newPW = changePW_eT.text.toString()
        val confirmNewPW = changeCPW_eT.text.toString()

        if (previousPW == userPW) {
            if (newPW == confirmNewPW) {
                currentUser?.updatePassword(newPW)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            userPW = newPW
                            updatePW(userPW)

                        } else {
                            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                        }
                    }


            } else Toast.makeText(applicationContext, "Password is not same", Toast.LENGTH_SHORT).show()

        } else Toast.makeText(applicationContext, "Check Your Password", Toast.LENGTH_SHORT).show()


    }
    private fun reAuthenticate(email: String,password: String){

        val newPW = changePW_eT.text.toString()
        val credential = EmailAuthProvider
            .getCredential(email,password)

        firebaseAuth?.currentUser?.reauthenticate(credential)
            ?.addOnCompleteListener(this){
                if(it.isSuccessful){
                    firebaseAuth?.currentUser?.updatePassword(newPW)
                        ?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                                userPW = newPW
                                updatePW(userPW)

                            } else {
                                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                            }
                        }

                }
            }
    }
    private fun updateUserInfo() {
        val updateFullName = userFullName_eT.text.toString()
        val userBirthY = datePicker.year
        val userBirthM = datePicker.month + 1
        val userBirthD = datePicker.dayOfMonth

        userBirth = "$userBirthY-$userBirthM-$userBirthD"

        userFullName = if (!TextUtils.isEmpty(updateFullName)) {
            updateFullName
        } else ""

        updateInfo(userBirth, userFullName)
    }

    private fun updateInfo(userFullName: String, userBirth: String) {
        val userDBReference = databaseReference?.child(userUid)

        userDBReference?.child("userFullName")!!.setValue(userFullName)
        userDBReference?.child("userBirth")!!.setValue(userBirth)

    }

    private fun updatePW(userPW: String) {
        val userDBReference = databaseReference?.child(userUid)
        userDBReference?.child("userPW")!!.setValue(userPW)
    }
}