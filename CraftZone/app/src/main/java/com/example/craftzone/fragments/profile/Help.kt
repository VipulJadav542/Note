package com.example.craftzone.fragments.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.craftzone.R
import com.example.craftzone.databinding.FragmentHelpBinding
import com.example.craftzone.databinding.FragmentHomeBinding
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment

class Help : Fragment() {

    lateinit var binding: FragmentHelpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHelpBinding.inflate(inflater)

        val img_close_help = binding.imgCloseHelp
        img_close_help.setOnClickListener {
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

        binding.imgEmail.setOnClickListener { openEmailApp() }
        binding.imgPhone.setOnClickListener { openCallApp() }
    }

    private fun openCallApp() {
        val callIntent: Intent = Uri.parse("tel:9023978985").let { number ->
            Intent(Intent.ACTION_DIAL, number)
        }.also { startActivity(it) }
    }

    private fun openEmailApp() {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("vipuljadav542@gmail.com")) // recipients
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_TEXT, "")
            putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
        }.also { startActivity(it) }
    }
}