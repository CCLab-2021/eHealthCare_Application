package com.example.ccn_ehealthcare.firebaseDB

class DoctorUserDB(val userID : String, val userNickName : String, val userFullName : String, val userEmail : String, val userPW : String, val userBirth : String, val hospitalName : String, val licenseNum : String, val userType : String) {
    constructor() : this("", "", "", "", "", "", "", "", "")
}