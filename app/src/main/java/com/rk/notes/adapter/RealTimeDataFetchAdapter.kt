package com.rk.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.rk.notes.dataModel.RealTimeData
import com.rk.notes.databinding.RetrievDataBinding

class RealTimeDataFetchAdapter(
    private var dataListItem: MutableList<RealTimeData>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RealTimeDataFetchAdapter.MyViewHolder>() {
    interface OnItemClickListener {
        fun onDeleteClick(id: String)
        fun onUpdateClick(data: RealTimeData)
    }

    inner class MyViewHolder(val binding: RetrievDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    private fun anim(view: View) {
        val animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1500
        view.startAnimation(animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RetrievDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        anim(holder.itemView)
        val note = dataListItem[position]
        holder.binding.title.text = note.title
        holder.binding.description.text = note.description
        holder.binding.date.text = note.dateTime
        holder.binding.Update.setOnClickListener {
            itemClickListener.onUpdateClick(
                note
            )
        }
        holder.binding.Delete.setOnClickListener {
            itemClickListener.onDeleteClick(note.noteId)
        }

    }

    fun updateNote(dataList: MutableList<RealTimeData>) {
        dataListItem.clear()
        dataListItem.addAll(dataList)
        notifyDataSetChanged()  //
    }
}