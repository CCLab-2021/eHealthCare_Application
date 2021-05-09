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
import kotlinx.android.synthetic.main.hospital_contents_layout.view.*

class hospitalAdapter(val contentsList : List<hospitalModel>) : RecyclerView.Adapter<hospitalAdapter.ViewHolder>(){
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var contentsName = itemView.contentName_tV

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hospital_contents_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contentsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contents : hospitalModel = contentsList[position]
        holder.contentsName.text = contents.contentNames


    }
}