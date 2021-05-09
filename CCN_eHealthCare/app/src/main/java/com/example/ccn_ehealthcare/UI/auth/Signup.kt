package com.example.ccn_ehealthcare.UI.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.doctor.DoctorHome
import com.example.ccn_ehealthcare.UI.patient.PatientHome
import com.example.ccn_ehealthcare.firebaseDB.UserDB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    companion object {
        private var USERUID = "USERUID"
        private var USERNICKNAME = "USERNICKNAME"
        private var USERFULLNAME = "USERFULLNAME"
        private var USEREMAIL = "USEREMAIL"
        private var USERPW = "USERPW"
        private var USERBIRTH = "USERBIRTH"
        private var USERTYPE = "USERTYPE"
    }

    lateinit var firebaseAuth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("User")

        buttonHandler()
    }

    private fun buttonHandler() {
        var userType = ""

        userType_Group.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.userPatient) {
                userType = "Patient"
                Log.e("USERTYPE", userType)
            }
            else if (checkedId == R.id.userDoctor) {
                userType = "Doctor"
                Log.e("USERTYPE", userType)
            }
        }

        register_btn.setOnClickListener {
            val userEmail = editEmail.text.toString()
            val userPW = editPW.text.toString()
            val userCPW = editCPW.text.toString()

            if (userPW == userCPW) createUser(userEmail, userPW, userType)
            else Toast.makeText(this, "Check your password again", Toast.LENGTH_SHORT).show()
        }

        goback_login_btn.setOnClickListener {
            var intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createUser(userEmail: String, userPW: String, userType: String) {
        var userID = ""
        val userNickName = editNickName.text.toString()
        val userBirth = ""
        val userFullName = ""

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPW)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    userID = firebaseAuth.currentUser!!.uid.toString()
                    Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show()

                    createDB(userID, userNickName, userFullName, userEmail, userPW, userBirth, userType)

                    Log.e("CreateUser", "Ok.")
                }
                else {
                    Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show()
                    Log.e("CreateUser", "Failed.")
                }
            }

    }

    private fun createDB(userID: String, userNickName: String, userFullName: String, userEmail: String, userPW: String, userBirth: String, userType: String) {
        Log.e("CreateDB", "Start")

        val currentUserDB = databaseReference?.child(userID)

        if (userType == "Patient") {
            currentUserDB?.setValue(UserDB(userID, userNickName, userFullName, userEmail, userPW, userBirth, userType))

            Log.e("CHECK", "id : ${userID}, nickname : ${userNickName}, fullname : ${userFullName}, email : ${userEmail}, pw : ${userPW}, birth : ${userBirth}, type : ${userType}")
        }
        else if (userType == "Doctor") {
            currentUserDB?.setValue(UserDB(userID, userNickName, userFullName, userEmail, userPW, userBirth, userType))

            Log.e("CHECK", "id : ${userID}, nickname : ${userNickName}, fullname : ${userFullName}, email : ${userEmail}, pw : ${userPW}, birth : ${userBirth}, type : ${userType}")
        }

        movePage(userID, userNickName, userFullName, userEmail, userPW, userBirth, userType)
    }

    private fun movePage(userID: String, userNickName: String, userFullName: String, userEmail: String, userPW: String, userBirth: String, userType: String) {
        when (userType) {
            "Patient" -> startActivity(Intent(this, PatientHome::class.java).apply {
                putExtra(USERUID, userID)
                putExtra(USERNICKNAME, userNickName)
                putExtra(USERFULLNAME, userFullName)
                putExtra(USEREMAIL, userEmail)
                putExtra(USERPW, userPW)
                putExtra(USERBIRTH, userBirth)
                putExtra(USERTYPE, userType)
            })
            "Doctor" -> startActivity(Intent(this, DoctorHome::class.java).apply {
                putExtra(USERUID, userID)
                putExtra(USERNICKNAME, userNickName)
                putExtra(USERFULLNAME, userFullName)
                putExtra(USEREMAIL, userEmail)
                putExtra(USERPW, userPW)
                putExtra(USERBIRTH, userBirth)
                putExtra(USERTYPE, userType)
            })
        }
    }
}