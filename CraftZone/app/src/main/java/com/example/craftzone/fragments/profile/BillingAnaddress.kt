package com.example.craftzone.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.craftzone.R
import com.example.craftzone.activities.Login
import com.example.craftzone.databinding.FragmentBillingAnaddressBinding
import com.example.craftzone.databinding.FragmentSearchBinding
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment

class BillingAnaddress : Fragment() {
    lateinit var binding: FragmentBillingAnaddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBillingAnaddressBinding.inflate(inflater)

        binding.imgCloseBilling.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, ProfileFragment(), "fragmnetId")
                ?.addToBackStack(null)
                ?.commit()
        }
        binding.imgAddAddress.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, Address(), "fragmnetId")
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

}