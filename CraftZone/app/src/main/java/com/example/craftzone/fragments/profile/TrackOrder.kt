package com.example.craftzone.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.craftzone.R
import com.example.craftzone.databinding.FragmentLanguageBinding
import com.example.craftzone.databinding.FragmentTrackOrderBinding
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment

class TrackOrder : Fragment() {
    lateinit var binding: FragmentTrackOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrackOrderBinding.inflate(inflater)

        binding.imgCloseOrder.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, ProfileFragment(), "fragmnetId")
                ?.addToBackStack(null)
                ?.commit()
        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, ProfileFragment(), "fragmnetId")
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return binding.root
    }
}