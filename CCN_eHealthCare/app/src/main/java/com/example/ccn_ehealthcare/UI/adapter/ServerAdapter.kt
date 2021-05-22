package com.example.ccn_ehealthcare.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.model.ServerModel
import kotlinx.android.synthetic.main.server_contents_layout.view.*

class ServerAdapter (val contentList : List<ServerModel>) : RecyclerView.Adapter<ServerAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var contentsName = itemView.contentName_tV
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.server_contents_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServerAdapter.ViewHolder, position: Int) {
        val content : ServerModel = contentList[position]
        holder.contentsName.text = content.contentNames
    }

    override fun getItemCount(): Int {
        return contentList.size
    }
}