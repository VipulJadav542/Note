package com.example.craftzone.fragments.bottomNavigation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.craftzone.R
import com.example.craftzone.activities.Home
import com.example.craftzone.activities.Login
import com.example.craftzone.activities.admin.Admin
import com.example.craftzone.databinding.FragmentProfileBinding
import com.example.craftzone.fragments.profile.Allorder
import com.example.craftzone.fragments.profile.Editprofile
import com.example.craftzone.fragments.profile.Help
import com.example.craftzone.fragments.profile.Language
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var preferences:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        firebaseFirestore=FirebaseFirestore.getInstance()

        preferences= activity?.getSharedPreferences("my_pre", Context.MODE_PRIVATE)!!
        val getemail=preferences.getString("email","1")
        val password=preferences.getString("password","123456")
        if(getemail =="vipuljadav542@gmail.com" && password =="123456")
        {
            binding.linearAdmin.visibility=View.VISIBLE
        }
        if(getemail != "1")
        {
            val name=binding.tvUserName
            binding.tvEditPersonalDetails.text=getemail.toString()
            firebaseFirestore.collection("EditProfile").document(getemail.toString()).get()
                .addOnSuccessListener {tasks ->
                    name.text = tasks.get("firstname").toString()
                    val imageURI=tasks.get("profilepicture").toString()
                    val uri = Uri.parse(imageURI)
                    Picasso.get().load(uri).into(binding.imgUser)
                }
        }

        val logout=binding.logout
        logout.setOnClickListener {
            preferences.edit()?.clear()?.apply()
            val intent = Intent (activity, Login::class.java)
            startActivity(intent)
            finishYourActivity()
//            fragmentManager?.beginTransaction()?.remove(this)?.commit()

        }
        val tvallorders=binding.allOrders
        tvallorders.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, Allorder(), "")
                    ?.addToBackStack(null)
                    ?.commit()
        }

        val language=binding.linearLanguage
        language.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout,Language(), "")
                ?.addToBackStack(null)
                ?.commit()
        }

        val help=binding.linearHelp
        help.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout,Help(), "")
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.constraintProfile.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout,Editprofile(), "")
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.linearTrackOrder.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.frameLayout,TrackOrder(), "fragmnetId")
//                ?.addToBackStack(null)
//                ?.commit()
            Toast.makeText(context, "this feature will be available soon", Toast.LENGTH_SHORT).show()
        }

        binding.linearBilling.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.frameLayout,BillingAnaddress(), "fragmnetId")
//                ?.addToBackStack(null)
//                ?.commit()
            Toast.makeText(context, "this feature will be available soon", Toast.LENGTH_SHORT).show()
        }

        val admin=binding.linearAdmin
        admin.setOnClickListener {
            val intent = Intent (activity, Admin::class.java)
            startActivity(intent)
        }

        val callback= object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)


        return binding.root
    }
    fun finishYourActivity() {
        Home.instance?.finish()
    }
}