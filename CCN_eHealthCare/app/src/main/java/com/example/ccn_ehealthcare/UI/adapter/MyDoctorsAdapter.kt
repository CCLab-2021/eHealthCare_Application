package com.example.ccn_ehealthcare.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.model.MyDoctorsModel
import kotlinx.android.synthetic.main.my_doctors_layout.view.*

class MyDoctorsAdapter (val doctorList : ArrayList<MyDoctorsModel>) : RecyclerView.Adapter<MyDoctorsAdapter.ViewHolder>() {
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data : MyDoctorsModel) {
            itemView.doctorsName_tV.text = data.doctorName
            itemView.specialty_tV.text = data.specialty
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDoctorsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_doctors_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyDoctorsAdapter.ViewHolder, position: Int) {
        holder.bindItems(doctorList[position])
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }
}