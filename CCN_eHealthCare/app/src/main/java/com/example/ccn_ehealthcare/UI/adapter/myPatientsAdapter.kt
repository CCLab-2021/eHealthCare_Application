package com.example.ccn_ehealthcare.UI.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.doctor.MyPatients
import com.example.ccn_ehealthcare.UI.model.myPatientsModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.doctor_reports_layout.view.*

class myPatientsAdapter(val patientsList : List<myPatientsModel>) : RecyclerView.Adapter<myPatientsAdapter.ViewHolder>(){

    private lateinit var clickListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        clickListener = listener
    }


    class ViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        var patientName = itemView.patientName_tV
        var patientAge = itemView.patientAge_tV
        var patientAddress = itemView.patientAddress_tV
        var patientReport : TextView = itemView.patientReport_eT
        var linearLayout : LinearLayout = itemView.linearLayout
        var expandableLayout : RelativeLayout = itemView.expandable_layout

        init {
         itemView.reportSave_btn.setOnClickListener {
             listener.onItemClick(adapterPosition)
         }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_reports_layout, parent, false)
        return ViewHolder(view, clickListener)
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
}
