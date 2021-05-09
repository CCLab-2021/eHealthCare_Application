package com.example.ccn_ehealthcare.firebaseDB

class UserDB(val userID : String, val userNickName : String, val userFullName : String, val userEmail : String, val userPW : String, val userBirth : String, val userType : String) {
    constructor() : this("", "", "", "", "", "", "")
}