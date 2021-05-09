package com.example.ccn_ehealthcare.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import com.example.ccn_ehealthcare.UI.model.myPatientsModel
import kotlinx.android.synthetic.main.doctor_reports_layout.view.*
import kotlinx.android.synthetic.main.hospital_contents_layout.view.*

class myPatientsAdapter(val patientsList : List<myPatientsModel>) : RecyclerView.Adapter<myPatientsAdapter.ViewHolder>(){
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var patientName = itemView.patientName_tV
        var patientAge = itemView.patientAge_tV
        var patientAddress = itemView.patientAddress_tV
        var patientReport = itemView.patientReport_tV
        var linearLayout : LinearLayout = itemView.linearLayout
        var expandableLayout : RelativeLayout = itemView.expandable_layout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_reports_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patientsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patients : myPatientsModel = patientsList[position]
        holder.patientName.text = patients.patientsName
        holder.patientAddress.text = patients.patientsAddress
        holder.patientAge.text = patients.patientsAge
        holder.patientReport.text = patients.report

        val isExpandable : Boolean = patientsList[position].expandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val patients = patientsList[position]
            patients.expandable = !patients.expandable
            notifyItemChanged(position)
        }
    }


//class myPatientsAdapter(val patientsList : List<myPatientsModel>) : RecyclerView.Adapter<myPatientsAdapter.ViewHolder>() {
//    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
//        var patientName = itemView.patientName_tV
//        var patientAge = itemView.patientAge_tV
//        var patientAddress = itemView.patientAddress_tV
//        var patientReport = itemView.patientAddress_tV
////
////    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_reports_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return patientsList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val patients : myPatientsModel = patientsList[position]
//        holder.patientName.text = patients.patientsName
//        holder.patientAddress.text = patients.patientsAddress
//        holder.patientAge.text = patients.patientsAge
//        holder.patientReport.text = patients.report
//
//
//    }

}