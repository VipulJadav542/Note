package com.example.craftzone.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.craftzone.R
import com.example.craftzone.activities.Home
import com.example.craftzone.databinding.FragmentAddressBinding
import com.example.craftzone.databinding.FragmentBillingAnaddressBinding
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment

class Address : Fragment() {

    lateinit var binding: FragmentAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentAddressBinding.inflate(inflater)

        binding.imgAddressClose.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, BillingAnaddress(), "fragmnetId")
                ?.addToBackStack(null)
                ?.commit()
        }
//        (requireActivity() as Home).supportActionBar!!.hide()




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
}