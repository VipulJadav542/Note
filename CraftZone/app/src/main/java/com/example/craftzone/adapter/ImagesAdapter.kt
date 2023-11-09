package com.example.craftzone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.craftzone.R
import com.example.craftzone.databinding.EachItemBinding
import com.squareup.picasso.Picasso

class ImagesAdapter(
    private var mImage: MutableList<String>,
    private var mPrice: MutableList<String>,
    private var mName: MutableList<String>,
    private var mDescription: MutableList<String>,
) :
    RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    inner class ImagesViewHolder(var binding: EachItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val title: TextView = holder.itemView.findViewById(R.id.title123)
        val desc: TextView = holder.itemView.findViewById(R.id.description123)
        val price: TextView = holder.itemView.findViewById(R.id.price123)
        with(holder.binding) {
            with(mImage[position]) {
                Picasso.get().load(this).into(imageView123)
            }
            with(mName[position]) {

                title.text = mName[position]
            }
            with(mDescription[position]) {

                desc.text = mDescription[position]
            }
            with(mPrice[position]) {

                price.text = "$ ${mPrice[position]}"
            }

        }
    }
    override fun getItemCount(): Int {
        return mImage.size
    }
}
