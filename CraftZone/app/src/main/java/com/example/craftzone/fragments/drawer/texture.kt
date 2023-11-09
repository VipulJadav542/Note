package com.example.craftzone.fragments.drawer


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.craftzone.Data.ProductItem
import com.example.craftzone.R
import com.example.craftzone.activities.ReceiveFragments
import com.example.craftzone.adapter.HomeRvAdapter1
import com.example.craftzone.adapter.textureAdapter
import com.example.craftzone.databinding.FragmentTextureBinding
import com.example.craftzone.fragments.bottomNavigation.HomeFragment
import com.google.firebase.firestore.FirebaseFirestore

class texture : Fragment() {
    private lateinit var binding: FragmentTextureBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: textureAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTextureBinding.inflate(inflater, container, false)

        initVars()
        getImages()

        val callback= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, HomeFragment(), "fragmnetId")
                    ?.addToBackStack(null)
                    ?.commit()
                Toast.makeText(context,"Back button pressed", Toast.LENGTH_LONG).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        return binding.root
    }
    private fun initVars() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(activity,2)
        adapter = textureAdapter(ArrayList())
        binding.recyclerView.adapter = adapter

        // Set a click listener for items
        adapter.onItemClick={
            val intent= Intent(context, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImages() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Texture")
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