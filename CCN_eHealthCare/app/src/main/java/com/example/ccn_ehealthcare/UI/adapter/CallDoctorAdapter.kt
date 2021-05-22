package com.example.ccn_ehealthcare.UI.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R

import com.example.ccn_ehealthcare.UI.model.CallDoctorModel

import kotlinx.android.synthetic.main.call_doctor_layout.view.*

class CallDoctorAdapter(val doctorList: List<CallDoctorModel>) : RecyclerView.Adapter<CallDoctorAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(v:View, data: CallDoctorModel, pos : Int)
    }
    private var listener : OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var doctorname = itemView.doctorsName_tV
        var doctornum = " "


        fun bind(item: CallDoctorModel) {
            doctorname.text = item.doctorName
            doctornum= item.phoneNum

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.callDoctor_btn.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                        var intent = Intent(Intent.ACTION_VIEW)
                        var a="tel:"+item.phoneNum
                        intent.setData(Uri.parse(a))
                        Log.e("전화번호", a)
                        ContextCompat.startActivity(itemView.context, intent, null)
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