package com.example.craftzone.fragments.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.craftzone.R
import com.example.craftzone.activities.Home
import com.example.craftzone.databinding.FragmentLanguageBinding
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment
import java.util.Locale

@Suppress("DEPRECATION")
class Language : Fragment() {
    private lateinit var binding: FragmentLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageBinding.inflate(inflater)

        binding.imgCloseLanguage.setOnClickListener {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentLanguage = Locale.getDefault().language

        Log.d("test", currentLanguage)
        when (currentLanguage) {
            "en" -> {
                changeToEnglish()
            }

            "gu" -> {
                changeToGujrati()
            }

            "hi" -> {
                changeToHindi()
            }
        }
        binding.linearEnglish.setOnClickListener {
            changeLanguage("en")
        }
        binding.gujrati.setOnClickListener {
            changeLanguage("gu")
        }
        binding.linearHindi.setOnClickListener {
            changeLanguage("hi")
        }
    }

    private fun changeToGujrati() {
        binding.apply {
            imgGujrati.visibility = View.VISIBLE
            imgEnglish.visibility = View.INVISIBLE
            imgHindi.visibility = View.INVISIBLE
        }
    }

    private fun changeToEnglish() {
        binding.apply {
            imgGujrati.visibility = View.INVISIBLE
            imgEnglish.visibility = View.VISIBLE
            imgHindi.visibility = View.INVISIBLE
        }
    }

    private fun changeToHindi() {
        binding.apply {
            imgGujrati.visibility = View.INVISIBLE
            imgEnglish.visibility = View.INVISIBLE
            imgHindi.visibility = View.VISIBLE

        }
    }

    private fun changeLanguage(code: String) {
        val intent = Intent(requireActivity(), Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val sharedPref = activity?.getSharedPreferences("language", Context.MODE_PRIVATE)
        sharedPref?.edit()?.putString("language", "en")?.apply()
        if (code == "en") {
            setLocal("en")
            sharedPref?.edit()?.putString("language", "en")?.apply()
            startActivity(intent)
        } else if (code == "gu") {
            setLocal("gu")
            sharedPref?.edit()?.putString("language", "gu")?.apply()
            startActivity(intent)
        } else if (code == "hi") {
            setLocal("hi")
            sharedPref?.edit()?.putString("language", "hi")?.apply()
            startActivity(intent)
        }
    }

    private fun setLocal(langCode: String) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val resources = context?.resources
        val config = resources?.configuration
        config?.locale = locale
        resources?.updateConfiguration(config, resources.displayMetrics)
    }
}