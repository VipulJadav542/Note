package com.example.craftzone.fragments.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.craftzone.Data.ProductItem
import com.example.craftzone.R
import com.example.craftzone.activities.ReceiveAddToCart
import com.example.craftzone.adapter.HomeRvAdapter1
import com.example.craftzone.databinding.FragmentAllorderBinding
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment
import com.google.firebase.firestore.FirebaseFirestore

class Allorder : Fragment() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: HomeRvAdapter1
    lateinit var binding:FragmentAllorderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAllorderBinding.inflate(inflater)



        initVars()
        getImages()


        binding.imgCloseLanguage.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, ProfileFragment(), "fragmnetId")
                ?.addToBackStack(null)
                ?.commit()
        }


        val callback= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, ProfileFragment(), "fragmnetId")
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        return binding.root
    }
    private fun initVars() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context ,
            LinearLayoutManager.VERTICAL,false)
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
        binding.progressbarAllOrders.visibility = View.VISIBLE
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
                binding.progressbarAllOrders.visibility = View.GONE
            }
    }
}