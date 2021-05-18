package com.example.ccn_ehealthcare.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.model.hospitalModel
import kotlinx.android.synthetic.main.hospital_contents_layout.view.*
import java.util.ArrayList


class hospitalAdapter(val contentsList : List<hospitalModel>) : RecyclerView.Adapter<hospitalAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(v:View, data: hospitalModel, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var contentsName = itemView.contentName_tV
        var url = itemView.url


        fun bind(item: hospitalModel) {
            contentsName.text = item.contentNames
            url.text = item.url

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.contentsDownload_btn.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hospital_contents_layout, parent, false)
        return ViewHolder(view)
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = ViewHolder(parent.inflate(R.layout.hospital_contents_layout))

    override fun getItemCount(): Int {
        return contentsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contentsList[position])

    }
}

