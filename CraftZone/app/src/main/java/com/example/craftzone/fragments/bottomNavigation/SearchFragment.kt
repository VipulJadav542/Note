package com.example.craftzone.fragments.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.craftzone.R
import com.example.craftzone.databinding.FragmentSearchBinding
import com.example.craftzone.fragments.drawer.ItemnotFound
import com.example.craftzone.fragments.drawer.chair
import com.example.craftzone.fragments.drawer.curtain_blind
import com.example.craftzone.fragments.drawer.decorative_panels
import com.example.craftzone.fragments.drawer.genuine_leather
import com.example.craftzone.fragments.drawer.mattress
import com.example.craftzone.fragments.drawer.modular_kitchen
import com.example.craftzone.fragments.drawer.pvc_wooden_flooring
import com.example.craftzone.fragments.drawer.recliner
import com.example.craftzone.fragments.drawer.room_partition
import com.example.craftzone.fragments.drawer.sofa
import com.example.craftzone.fragments.drawer.sofa_with_bed
import com.example.craftzone.fragments.drawer.texture
import com.example.craftzone.fragments.drawer.upholstery_bed
import com.example.craftzone.fragments.drawer.wardrobe

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)


        val products=resources.getStringArray(R.array.products)

        val arrayAdapter = context?.let { ArrayAdapter(it,android.R.layout.simple_expandable_list_item_1,products) }
        binding.searchView.setAdapter(arrayAdapter)
        category = binding.searchView.toString()

        binding.search.setOnClickListener {
            if (binding.searchView.text.toString() == "Chair" || binding.searchView.text.toString() == "chair") {
                chageFragment(chair())
            } else if (binding.searchView.text.toString() == "Curtain Blind" || binding.searchView.text.toString() == "curtain blind")
                run {
                    chageFragment(curtain_blind())
                }
            else if (binding.searchView.text.toString() == "Genuine Leather" || binding.searchView.text.toString() == "genuine leather")
                run {
                    chageFragment(genuine_leather())
                }
            else if (binding.searchView.text.toString() == "Mattress" || binding.searchView.text.toString() == "mattress")
                run {
                    chageFragment(mattress())
                }
            else if (binding.searchView.text.toString() == "Modular Kitchen" || binding.searchView.text.toString() == "modular kitchen")
                run {
                    chageFragment(modular_kitchen())
                }
            else if (binding.searchView.text.toString() == "PVC Wooden Flooring" || binding.searchView.text.toString() == "pvc wooden flooring")
                run {
                    chageFragment(pvc_wooden_flooring())
                }
            else if (binding.searchView.text.toString() == "Recliner" || binding.searchView.text.toString() == "recliner")
                run {
                    chageFragment(recliner())
                }
            else if (binding.searchView.text.toString() == "Room Partition" || binding.searchView.text.toString() == "room partition")
                run {
                    chageFragment(room_partition())
                }
            else if (binding.searchView.text.toString() == "Sofa" || binding.searchView.text.toString() == "sofa")
                run {
                    chageFragment(sofa())
                }
            else if (binding.searchView.text.toString() == "Sofa with Bed" || binding.searchView.text.toString() == "sofa with bed")
                run {
                    chageFragment(sofa_with_bed())
                }
            else if (binding.searchView.text.toString() == "Texture" || binding.searchView.text.toString() == "texture")
                run {
                    chageFragment(texture())
                }
            else if (binding.searchView.text.toString() == "Upholstery Bed" || binding.searchView.text.toString() == "upholstery bed")
                run {
                    chageFragment(upholstery_bed())
                }
            else if (binding.searchView.text.toString() == "Wardrobe" || binding.searchView.text.toString() == "wardrobe")
                run {
                    chageFragment(wardrobe())
                }
            else if (binding.searchView.text.toString() == "Decorative Panels" || binding.searchView.text.toString() == "decorative panels")
                run {
                    chageFragment(decorative_panels())
                }
            else
                run {
                    chageFragment(ItemnotFound())
                }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return binding.root
    }
    private fun chageFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frameLayout1, fragment, "fragmnetId")
            ?.addToBackStack(null)
            ?.commit()
    }
}