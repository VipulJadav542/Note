package com.example.craftzone.activities.admin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.craftzone.databinding.ActivityAdminBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Admin : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firebasefirestore: FirebaseFirestore
    private lateinit var itemname: EditText
    private lateinit var description: EditText
    private lateinit var price: EditText
    private lateinit var category: String
    private lateinit var selectspinner:Spinner
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Admin Panel"

        itemname = binding.itemname
        description = binding.description
        price = binding.price
        selectspinner=binding.selectSPinner

        val selectedItem= arrayOf("Chair","Curtain Blind","Genuine Leather","Mattress","Modular Kitchen","PVC Wooden Flooring","Recliner","Room Partition","Sofa","Sofa cum Bed","Texture","Upholstery Bed","Wardrobe","Decorative Panels")
        val arrayAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,selectedItem)
        selectspinner.adapter=arrayAdapter
        selectspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category=selectedItem[position]
//                Toast.makeText(this@Admin,"item is ${selectedItem[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@Admin,"Nothing is selected", Toast.LENGTH_SHORT).show()
            }
        }
        initVars()
        registerClickEvents()
    }
    private fun registerClickEvents() {
        binding.uploadBtn.setOnClickListener {
            if (checking()) {
                uploadImage()
//                clearField()
            } else {
                Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.delete.setOnClickListener {
            startActivity(Intent(this, ReceiveAdmin::class.java))
        }

        binding.imageView.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        imageUri = it
        binding.imageView.setImageURI(it)
    }

    private fun initVars() {

        storageRef = FirebaseStorage.getInstance().reference.child("ProductPictures")
        firebasefirestore = FirebaseFirestore.getInstance()
    }
    private fun checking(): Boolean {
        if (itemname.text.trim { it <= ' '}.toString().isNotEmpty() &&
            description.text.trim { it <= ' ' }.toString().isNotEmpty() &&
            price.text.toString().trim { it <= ' ' }.isNotEmpty() &&
            category.trim { it <= ' '}.isNotEmpty()
        ) {
            return true
        }
        return false
    }
    private fun uploadImage() {
        if (imageUri == null) {
            binding.constaintlayout1.visibility = View.INVISIBLE
            binding.constaintlayout2.alpha=1f
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }
        binding.constaintlayout1.visibility = View.VISIBLE
        binding.constaintlayout2.alpha=0.3f
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            binding.textView8.visibility = View.GONE
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val map = hashMapOf(
                            "pic" to uri.toString(),
                            "category" to category,
                            "title" to itemname.text.toString(),
                            "description" to description.text.toString(),
                            "price" to price.text.toString(),
                            "email" to "vjadav219@rku.ac.in"
                        )
                        firebasefirestore.collection("ProductDetails").add(map)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Uploaded Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        firestoreTask.exception?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                binding.itemname.setText("")
                                binding.description.setText("")
                                binding.price.setText("")
                                binding.textView8.visibility = View.GONE
                                binding.imageView.setImageResource(0)
                                binding.constaintlayout1.visibility = View.INVISIBLE
                                binding.constaintlayout2.alpha=1f
                            }
                    }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    binding.textView8.visibility = View.GONE
                    binding.constaintlayout1.visibility = View.INVISIBLE
                    binding.constaintlayout2.alpha=1f
                }
            }
        }
    }
}