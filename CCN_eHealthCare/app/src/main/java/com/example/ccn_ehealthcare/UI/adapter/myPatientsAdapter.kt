package com.example.ccn_ehealthcare.UI.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.doctor.MyPatients
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import com.example.ccn_ehealthcare.UI.model.myPatientsModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.doctor_reports_layout.view.*
import kotlinx.android.synthetic.main.hospital_contents_layout.view.*

class myPatientsAdapter(val patientsList : List<myPatientsModel>) : RecyclerView.Adapter<myPatientsAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(v:View, data: myPatientsModel, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var patientName = itemView.patientName_tV
        var patientAge = itemView.patientAge_tV
        var patientAddress = itemView.patientAddress_tV
        var patientReport : EditText = itemView.patientReport_eT
        var linearLayout : LinearLayout = itemView.linearLayout
        var expandableLayout : RelativeLayout = itemView.expandable_layout
        var t_patientReport = ""

        fun bind(item: myPatientsModel) {
            patientName.text = item.patientsName
            patientAge.text = item.patientsAge.toString()
            patientAddress.text = item.patientsAddress
            t_patientReport = item.report
            patientReport.setText(t_patientReport)

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION) {
                itemView.reportSave_btn.setOnClickListener{
                    listener?.onItemClick(itemView, item ,pos)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_reports_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patientsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(patientsList[position])
        val isExpandable : Boolean = patientsList[position].expandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val patients = patientsList[position]
            patients.expandable = !patients.expandable
            notifyItemChanged(position)
        }
    }
}
