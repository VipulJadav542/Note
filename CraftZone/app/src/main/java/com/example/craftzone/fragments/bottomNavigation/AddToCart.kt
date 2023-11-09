package com.example.craftzone.fragments.bottomNavigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.craftzone.Data.ProductItem
import com.example.craftzone.activities.ReceiveAddToCart
import com.example.craftzone.adapter.HomeRvAdapter1
import com.example.craftzone.databinding.ActivityAddToCartBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddToCart : Fragment() {
    private lateinit var binding:ActivityAddToCartBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: HomeRvAdapter1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityAddToCartBinding.inflate(inflater, container, false)

        initVars()
        getImages()

        val callback= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)

        return binding.root
    }
    private fun initVars() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context ,LinearLayoutManager.VERTICAL,false)
        adapter = HomeRvAdapter1(ArrayList())
        binding.recyclerView.adapter = adapter
        adapter.onItemClick={
            val intent= Intent( context, ReceiveAddToCart::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImages() {
        val sharedpref = activity?.getSharedPreferences("my_pre", Context.MODE_PRIVATE)!!
        val email=sharedpref.getString("email","").toString()
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("AddtoCart")
            .whereEqualTo("email",email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<ProductItem>()
                for (document in querySnapshot) {
                    val productItem = ProductItem(
                        document.data["pic"].toString(),
                        document.data["title"].toString(),
                        document.data["description"].toString(),
                        document.data["price"].toString()
                    )
                    productList.add(productItem)
                }
                adapter.foodlist.addAll(productList)
                adapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }
}

