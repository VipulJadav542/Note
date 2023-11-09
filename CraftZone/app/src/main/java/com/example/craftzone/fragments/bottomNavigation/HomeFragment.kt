package com.example.craftzone.fragments.bottomNavigation

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
import androidx.recyclerview.widget.RecyclerView
import com.example.craftzone.Data.ProductItem
import com.example.craftzone.R
import com.example.craftzone.activities.ReceiveFragments
import com.example.craftzone.adapter.chairAdapter
import com.example.craftzone.adapter.curtain_blindAdapter
import com.example.craftzone.adapter.decorative_panelsAdapter
import com.example.craftzone.adapter.genuine_leatherAdapter
import com.example.craftzone.adapter.mattressAdapter
import com.example.craftzone.adapter.modular_kitchenAdapter
import com.example.craftzone.adapter.pvcwoodenflooringAdapter
import com.example.craftzone.adapter.reclinerAdapter
import com.example.craftzone.adapter.room_partitionAdapter
import com.example.craftzone.adapter.sofaAdapter
import com.example.craftzone.adapter.sofa_with_bedAdapter
import com.example.craftzone.adapter.textureAdapter
import com.example.craftzone.adapter.upholstery_bedAdapter
import com.example.craftzone.adapter.wardrobeAdapter
import com.example.craftzone.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var chairadapter: chairAdapter
    private lateinit var curtainBlindadapter: curtain_blindAdapter
    private lateinit var genuineLeatheradapter: genuine_leatherAdapter
    private lateinit var mattressAdapter: mattressAdapter
    private lateinit var modularKitchenadapter: modular_kitchenAdapter
    private lateinit var pvcwoodenflooringAdapter: pvcwoodenflooringAdapter
    private lateinit var reclinerAdapter: reclinerAdapter
    private lateinit var roomPartitionadapter: room_partitionAdapter
    private lateinit var sofaAdapter: sofaAdapter
    private lateinit var sofaCumBedadapter: sofa_with_bedAdapter
    private lateinit var textureAdapter: textureAdapter
    private lateinit var upholsteryBedadapter: upholstery_bedAdapter
    private lateinit var wardrobeAdapter: wardrobeAdapter
    private lateinit var decorativePanelsadapter: decorative_panelsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)


        initVarschairs()
        getImageschairs()

        initVarscurtain_blind()
        getImagescurtain_blind()

        initVarsgenuine_leather()
        getImagesgenuine_leather()

        initVarsmattress()
        getImagesmattress()

        initVarsmodular_kitchen()
        getImagesmodular_kitchen()

        initVarspvc_wooden_floofing()
        getImagespvc_wooden_floofing()

        initVarsrecliner()
        getImagesrecliner()

        initVarsroom_partition()
        getImagesroom_partition()

        initVarssofa()
        getImagessofa()

        initVarssofa_cum_bed()
        getImagessofa_cum_bed()

        initVarstexture()
        getImagestexture()

        initVarsupholstery_bed()
        getImagesupholstery_bed()

        initVarswardrobe()
        getImageswardrobe()

        initVarsdecorativepanels()
        getImagesdecorativepanels()

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
    private fun initVarschairs() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.chairRecyclerView.setHasFixedSize(true)
        binding.chairRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.chairadapter = chairAdapter(ArrayList())
        binding.chairRecyclerView.adapter = this.chairadapter

        // Set a click listener for items
        this.chairadapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImageschairs() {
        binding.constaintlayout11.visibility = View.VISIBLE
        binding.linearLayout2.visibility = View.GONE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Chair")
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
                this.chairadapter.foodlist.addAll(productList)
                this.chairadapter.notifyDataSetChanged()
                binding.constaintlayout11.visibility = View.GONE
                binding.linearLayout2.visibility=View.VISIBLE
            }
    }

    //Second recycler view
    private fun initVarscurtain_blind() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.curtainBlindRecyclerView.setHasFixedSize(true)
        binding.curtainBlindRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.curtainBlindadapter = curtain_blindAdapter(ArrayList())
        binding.curtainBlindRecyclerView.adapter = this.curtainBlindadapter

        this.curtainBlindadapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagescurtain_blind() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Curtain Blind")
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
                this.curtainBlindadapter.foodlist.addAll(productList)
                this.curtainBlindadapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarsgenuine_leather() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.genuineLeatherRecyclerView.setHasFixedSize(true)
        binding.genuineLeatherRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.genuineLeatheradapter = genuine_leatherAdapter(ArrayList())
        binding.genuineLeatherRecyclerView.adapter = this.genuineLeatheradapter

        this.genuineLeatheradapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesgenuine_leather() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Genuine Leather")
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
                this.genuineLeatheradapter.foodlist.addAll(productList)
                this.genuineLeatheradapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarsmattress() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.mattressRecyclerView.setHasFixedSize(true)
        binding.mattressRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.mattressAdapter = mattressAdapter(ArrayList())
        binding.mattressRecyclerView.adapter = this.mattressAdapter

        this.mattressAdapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesmattress() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Mattress")
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
                this.mattressAdapter.foodlist.addAll(productList)
                this.mattressAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarsmodular_kitchen() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.modularKitchenRecyclerView.setHasFixedSize(true)
        binding.modularKitchenRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.modularKitchenadapter = modular_kitchenAdapter(ArrayList())
        binding.modularKitchenRecyclerView.adapter = this.modularKitchenadapter

        this.modularKitchenadapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesmodular_kitchen() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Modular Kitchen")
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
                this.modularKitchenadapter.foodlist.addAll(productList)
                this.modularKitchenadapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarspvc_wooden_floofing() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.pvcWoodenFlooringRecyclerView.setHasFixedSize(true)
        binding.pvcWoodenFlooringRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.pvcwoodenflooringAdapter = pvcwoodenflooringAdapter(ArrayList())
        binding.pvcWoodenFlooringRecyclerView.adapter = this.pvcwoodenflooringAdapter

        this.pvcwoodenflooringAdapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagespvc_wooden_floofing() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","PVC Wooden Flooring")
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
                this.pvcwoodenflooringAdapter.foodlist.addAll(productList)
                this.pvcwoodenflooringAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarsrecliner() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.reclinerRecyclerView.setHasFixedSize(true)
        binding.reclinerRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.reclinerAdapter = reclinerAdapter(ArrayList())
        binding.reclinerRecyclerView.adapter = this.reclinerAdapter

        this.reclinerAdapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesrecliner() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Recliner")
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
                this.reclinerAdapter.foodlist.addAll(productList)
                this.reclinerAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarsroom_partition() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.roomPartitionRecyclerView.setHasFixedSize(true)
        binding.roomPartitionRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.roomPartitionadapter = room_partitionAdapter(ArrayList())
        binding.roomPartitionRecyclerView.adapter = this.roomPartitionadapter

        this.roomPartitionadapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesroom_partition() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Room Partition")
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
                this.roomPartitionadapter.foodlist.addAll(productList)
                this.roomPartitionadapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarssofa() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.sofaRecyclerView.setHasFixedSize(true)
        binding.sofaRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.sofaAdapter = sofaAdapter(ArrayList())
        binding.sofaRecyclerView.adapter = this.sofaAdapter

        this.sofaAdapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagessofa() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Sofa")
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
                this.sofaAdapter.foodlist.addAll(productList)
                this.sofaAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarssofa_cum_bed() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.sofaCumBedRecyclerView.setHasFixedSize(true)
        binding.sofaCumBedRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.sofaCumBedadapter = sofa_with_bedAdapter(ArrayList())
        binding.sofaCumBedRecyclerView.adapter = this.sofaCumBedadapter

        this.sofaCumBedadapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagessofa_cum_bed() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Sofa cum Bed")
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
                this.sofaCumBedadapter.foodlist.addAll(productList)
                this.sofaCumBedadapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }
    private fun initVarstexture() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.textureRecyclerView.setHasFixedSize(true)
        binding.textureRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.textureAdapter = textureAdapter(ArrayList())
        binding.textureRecyclerView.adapter = this.textureAdapter

        this.textureAdapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagestexture() {
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
                this.textureAdapter.foodlist.addAll(productList)
                this.textureAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }
    private fun initVarsupholstery_bed() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.upholsteryBedRecyclerView.setHasFixedSize(true)
        binding.upholsteryBedRecyclerView.layoutManager = GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.upholsteryBedadapter = upholstery_bedAdapter(ArrayList())
        binding.upholsteryBedRecyclerView.adapter = this.upholsteryBedadapter

        this.upholsteryBedadapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesupholstery_bed() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Upholstery Bed")
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
                this.upholsteryBedadapter.foodlist.addAll(productList)
                this.upholsteryBedadapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarswardrobe() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.wardrobeRecyclerView.setHasFixedSize(true)
        binding.wardrobeRecyclerView.layoutManager =GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.wardrobeAdapter = wardrobeAdapter(ArrayList())
        binding.wardrobeRecyclerView.adapter = this.wardrobeAdapter

        this.wardrobeAdapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImageswardrobe() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Wardrobe")
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
                this.wardrobeAdapter.foodlist.addAll(productList)
                this.wardrobeAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun initVarsdecorativepanels() {
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.decorativePanelsRecyclerView.setHasFixedSize(true)
        binding.decorativePanelsRecyclerView.layoutManager =GridLayoutManager(activity,2 ,RecyclerView.HORIZONTAL, false)
        this.decorativePanelsadapter = decorative_panelsAdapter(ArrayList())
        binding.decorativePanelsRecyclerView.adapter = this.decorativePanelsadapter

        this.decorativePanelsadapter.onItemClick={
            val intent= Intent( activity, ReceiveFragments::class.java )
            intent.putExtra("food",it)
            startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesdecorativepanels() {
        binding.progressBar.visibility = View.VISIBLE
        firebaseFirestore.collection("ProductDetails")
            .whereEqualTo("category","Decorative Panel")
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
                this.decorativePanelsadapter.foodlist.addAll(productList)
                this.decorativePanelsadapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
    }
}