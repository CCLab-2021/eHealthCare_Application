package com.example.ccn_ehealthcare.UI.adapter

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.model.MyDoctorsModel
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import com.example.ccn_ehealthcare.UI.patient.PatientServers
import kotlinx.android.synthetic.main.call_doctor_layout.view.*



class callDocAdapter(val doctorList: List<MyDoctorsModel>) : RecyclerView.Adapter<callDocAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(v:View, data: MyDoctorsModel, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var doctorname = itemView.doctorsName_tV
        var doctornum = ""


        fun bind(item: MyDoctorsModel) {
            doctorname.text = item.doctorName
            doctornum= item.phoneNum

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.calling.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
//                        var intent = Intent(Intent.ACTION_VIEW)
//                        var a = ""
//                        a="tel:"+item.phoneNum
//                        intent.data= Uri.parse(a)
//                        Log.e("전화번호", doctornum)
//                        ContextCompat.startActivity(itemView.context, intent, null)
                }
            }


        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.call_doctor_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(doctorList[position])


    }



    override fun getItemCount(): Int {
        return doctorList.size
    }


}