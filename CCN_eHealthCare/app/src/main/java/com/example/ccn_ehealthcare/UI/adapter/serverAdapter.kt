package com.example.ccn_ehealthcare.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.model.serverModel
import kotlinx.android.synthetic.main.server_contents_layout.view.*

class serverAdapter (val contentList : List<serverModel>) : RecyclerView.Adapter<serverAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var contentsName = itemView.contentName_tV
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): serverAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.server_contents_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: serverAdapter.ViewHolder, position: Int) {
        val content : serverModel = contentList[position]
        holder.contentsName.text = content.contentNames
    }

    override fun getItemCount(): Int {
        return contentList.size
    }
}