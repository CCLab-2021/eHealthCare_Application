package com.example.ccn_ehealthcare.UI.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.doctor.DoctorHome
import com.example.ccn_ehealthcare.UI.patient.PatientHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

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
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("User")

        val currentUser = firebaseAuth.currentUser

        autoLogin(currentUser)

        buttonHandler()

    }

    private fun buttonHandler() {
        login_btn.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPW.text.toString()

            loginWithEmail(email, password)
        }

        signup_btn.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun autoLogin(currentUser: FirebaseUser?) {
        if (currentUser != null) readDB(currentUser)
    }

    private fun loginWithEmail(email: String, password: String) {
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val currentUser = firebaseAuth.currentUser
                    readDB(currentUser)
                }
                else {
                    Log.e("ERROR", it.exception.toString())
                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun readDB(currentUser: FirebaseUser?) {
        val userID = currentUser!!.uid
        val userDBReference = databaseReference?.child(userID)

        userDBReference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("READDB", error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("userType").value.toString() == "Patient") {
                    val userNickName = snapshot.child("userNickName").value.toString()
                    val userPW = snapshot.child("userPW").value.toString()
                    val userBirth = snapshot.child("userBirth").value.toString()
                    val userFullName = snapshot.child("userFullName").value.toString()
                    val userEmail = snapshot.child("userEmail").value.toString()
                    val enterUserType = "Patient"

                    movePage(userID, userNickName, userPW, userBirth, userFullName, userEmail, enterUserType)
                }
                else if (snapshot.child("userType").value.toString() == "Doctor") {
                    val userNickName = snapshot.child("userNickName").value.toString()
                    val userPW = snapshot.child("userPW").value.toString()
                    val userBirth = snapshot.child("userBirth").value.toString()
                    val userFullName = snapshot.child("userFullName").value.toString()
                    val userEmail = snapshot.child("userEmail").value.toString()
                    val enterUserType = "Doctor"

                    movePage(userID, userNickName, userPW, userBirth, userFullName, userEmail, enterUserType)
                }
            }
        })

    }

    private fun movePage(userID: String, userNickName: String, userPW: String, userBirth: String, userFullName: String, userEmail: String, enterUserType: String) {
        when (enterUserType) {
            "Patient" -> startActivity(Intent(this, PatientHome::class.java).apply {
                putExtra(USERUID, userID)
                putExtra(USERNICKNAME, userNickName)
                putExtra(USERFULLNAME, userFullName)
                putExtra(USEREMAIL, userEmail)
                putExtra(USERPW, userPW)
                putExtra(USERBIRTH, userBirth)
                putExtra(USERTYPE, enterUserType)
            })
            "Doctor" -> startActivity(Intent(this, DoctorHome::class.java).apply {
                putExtra(USERUID, userID)
                putExtra(USERNICKNAME, userNickName)
                putExtra(USERFULLNAME, userFullName)
                putExtra(USEREMAIL, userEmail)
                putExtra(USERPW, userPW)
                putExtra(USERBIRTH, userBirth)
                putExtra(USERTYPE, enterUserType)
            })
        }
    }
}