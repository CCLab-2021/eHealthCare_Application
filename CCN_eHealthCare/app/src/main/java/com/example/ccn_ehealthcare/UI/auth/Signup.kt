package com.example.ccn_ehealthcare.UI.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.doctor.DoctorHome
import com.example.ccn_ehealthcare.UI.patient.PatientHome
import com.example.ccn_ehealthcare.firebaseDB.DoctorUserDB
import com.example.ccn_ehealthcare.firebaseDB.PatientUserDB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.signup_doctor_info_dialog.view.*

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

    var hospitalName = ""
    var licenseNum = ""

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
            }
            else if (checkedId == R.id.userDoctor) {
                userType = "Doctor"
            }
        }

        register_btn.setOnClickListener {
            val userEmail = editEmail.text.toString()
            val userPW = editPW.text.toString()
            val userCPW = editCPW.text.toString()


            if (userPW == userCPW) {
                if (userType == "Patient") {
                    createUser(userEmail, userPW, userType)
                }
                else {
                    val doctorDialogView = LayoutInflater.from(this).inflate(R.layout.signup_doctor_info_dialog, null)
                    val dialogBuilder = AlertDialog.Builder(this)
                            .setView(doctorDialogView)

                    val doctorInfoDialog = dialogBuilder.show()

                    doctorDialogView.completeSignup_btn.setOnClickListener {
                        doctorInfoDialog.dismiss()
                        hospitalName = doctorDialogView.doctorHospitalName_eT.text.toString()
                        licenseNum = doctorDialogView.doctorLicenseNum_eT.text.toString()

                        createUser(userEmail, userPW, userType)
                    }
                }
            }

            else Toast.makeText(this, "Check your password again", Toast.LENGTH_SHORT).show()
        }

        goback_login_btn.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
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
                }
                else {
                    Log.e("SIGNUPFAILED", task.result.toString())
                    Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun createDB(userID: String, userNickName: String, userFullName: String, userEmail: String, userPW: String, userBirth: String, userType: String) {
        val currentUserDB = databaseReference?.child(userID)

        if (userType == "Patient") {
            currentUserDB?.setValue(PatientUserDB(userID, userNickName, userFullName, userEmail, userPW, userBirth, userType))

        }
        else if (userType == "Doctor") {
            currentUserDB?.setValue(DoctorUserDB(userID, userNickName, userFullName, userEmail, userPW, userBirth, hospitalName, licenseNum, userType))

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