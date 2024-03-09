package com.rk.notes.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.rk.notes.DataModel.RealTimeData
import com.rk.notes.databinding.RetrievDataBinding

class RealTimeDataFetchAdapter(
    private var datalistItem: MutableList<RealTimeData>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RealTimeDataFetchAdapter.MyViewHolder>() {
    interface OnItemClickListener {
        fun OnDeleteClick(Id: String)
        fun OnUpdateClick(Id: String, title: String, description: String,email: String,date: String)
    }

    inner class MyViewHolder(val binding: RetrievDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
    fun anim(view: View) {
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1500
        view.startAnimation(animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RetrievDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalistItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        anim(holder.itemView)
        val note = datalistItem[position]
        holder.binding.title.text = note.title
        holder.binding.description.text = note.description
        holder.binding.date.text=note.date_time
        holder.binding.Update.setOnClickListener {
            itemClickListener.OnUpdateClick(note.noteId, note.title, note.description,note.email,note.date_time)
        }
        holder.binding.Delete.setOnClickListener {
            itemClickListener.OnDeleteClick(note.noteId)
        }

    }
}