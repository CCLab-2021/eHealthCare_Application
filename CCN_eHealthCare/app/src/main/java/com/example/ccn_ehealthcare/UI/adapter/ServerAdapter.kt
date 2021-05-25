package com.example.ccn_ehealthcare.UI.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ccn_ehealthcare.R
import com.example.ccn_ehealthcare.UI.model.ServerModel
import kotlinx.android.synthetic.main.server_contents_layout.view.*
import kotlinx.android.synthetic.main.server_contents_layout.view.contentName_tV
import kotlinx.android.synthetic.main.server_contents_layout.view.contentsDownload_btn
import kotlinx.android.synthetic.main.server_contents_layout.view.url


class ServerAdapter (val contentList : List<ServerModel>) : RecyclerView.Adapter<ServerAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(v:View, data: ServerModel, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var contentsName = itemView.contentName_tV
        var url = itemView.url

        fun bind(item: ServerModel) {
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.server_contents_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contentList[position])
    }

    override fun getItemCount(): Int {
        return contentList.size
    }
}