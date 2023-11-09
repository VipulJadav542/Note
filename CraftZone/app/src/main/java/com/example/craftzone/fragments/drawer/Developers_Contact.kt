package com.example.craftzone.fragments.drawer

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.craftzone.databinding.FragmentDevelopersContactBinding

class Developers_Contact : Fragment()
{
    private lateinit var binding: FragmentDevelopersContactBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDevelopersContactBinding.inflate(inflater, container, false)
        binding.vipullinkdin.setOnClickListener {

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.linkedin.com/in/jadav-vipul-a8bb74239")
            startActivity(openURL)
        }

        binding.myusilinkdin.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.linkedin.com/in/myusidadhaniya/")
            startActivity(openURL)
        }

        binding.vipulemail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("vipuljadav542@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(emailIntent)
        }

        binding.myusiemail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("mdadhaniya436@rku.ac.in"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(emailIntent)
        }

        binding.vipulgithub.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://github.com/VipulJadav542")
            startActivity(openURL)
        }

        binding.myusigithub.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://github.com/myusi2705")
            startActivity(openURL)
        }

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Allow any orientation when the fragment is destroyed
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}