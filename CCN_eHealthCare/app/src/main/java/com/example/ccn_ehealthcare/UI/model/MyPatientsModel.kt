package com.example.ccn_ehealthcare.UI.model

import android.widget.Button

data class MyPatientsModel(val patientsName : String = "", val patientsAddress : String = "", val patientsAge : String = "", val report : String = "", var expandable : Boolean = false)