package com.example.craftzone.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.craftzone.Data.ProductItem
import com.example.craftzone.R

class HomeRvAdapter1(val foodlist: ArrayList<ProductItem>) :RecyclerView.Adapter<HomeRvAdapter1.ProductViewHolder>()
{
    var onItemClick:((ProductItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.single_list,parent,false)
        return ProductViewHolder(view)
    }
@SuppressLint("SetTextI18n")
override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    val food = foodlist[position]

//    when (food.image) {
////        is  Int-> {
////            // If it's an Int, assume it's a resource ID
////            holder.imageview.setImageResource(food.image)
////        }
//        is String -> {
//            holder.imageview.setImageResource(R.drawable.p1)
//        }
//        else -> {
//            holder.imageview.setImageResource(R.drawable.p1)
//        }
//    }

    val imageView = food.image
    val uri = Uri.parse(imageView)
    Glide.with(holder.itemView.context).load(uri).into(holder.imageview)
    holder.title.text = food.name
    holder.des.text = food.des
    holder.price.text = "price: $${food.price.toString()}"
    holder.price.setOnClickListener {

    }
    holder.itemView.setOnClickListener {
        onItemClick?.invoke(food)
    }
}
    override fun getItemCount(): Int {
        return foodlist.size
    }
    class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val imageview:ImageView=itemView.findViewById(R.id.imageSearch)
        val title:TextView=itemView.findViewById(R.id.titleSearch)
        val des:TextView=itemView.findViewById(R.id.descriptionSearch)
        val price:TextView=itemView.findViewById(R.id.priceSearch)
    }

}