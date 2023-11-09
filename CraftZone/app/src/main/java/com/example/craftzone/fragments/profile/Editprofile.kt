package com.example.craftzone.fragments.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.craftzone.R
import com.example.craftzone.activities.ResetPassword
import com.example.craftzone.databinding.FragmentEditprofileBinding
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class Editprofile : Fragment() {
    lateinit var binding: FragmentEditprofileBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var fname: EditText
    private lateinit var lname: EditText
    private lateinit var email: String
    private lateinit var password: TextView
    private lateinit var getImageString: String
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditprofileBinding.inflate(inflater)

        password = binding.tvUpdatePassword

        val preferences = activity?.getSharedPreferences("my_pre", Context.MODE_PRIVATE)
        email = preferences?.getString("email", "1").toString()
        binding.edEmail.hint = email
        binding.edEmail.setOnClickListener {
            Toast.makeText(context, "email is not editable", Toast.LENGTH_LONG).show()
        }

        binding.imgCloseEditProfile.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frameLayout, ProfileFragment(), "fragmnetId")
                ?.addToBackStack(null)
                ?.commit()
        }
        binding.tvUpdatePassword.setOnClickListener {
            val intent = Intent(activity, ResetPassword::class.java)
            startActivity(intent)
        }

        initVars()

        fname = binding.edFirstName
        lname = binding.edLastName

        if (email != "1") {
            firebaseFirestore.collection("EditProfile").document(email).get()
                .addOnSuccessListener { tasks ->
                    fname.hint = tasks.get("firstname").toString()
                    lname.hint = tasks.get("lastname").toString()
                    getImageString = tasks.get("profilepicture").toString()
                    val uri = Uri.parse(getImageString)
                    Picasso.get().load(uri).into(binding.imgUser)
                }
                .addOnFailureListener { tasks ->
                    Toast.makeText(context, tasks.toString(), Toast.LENGTH_LONG).show()

                }
        }
        binding.imgEdit.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        binding.btnSaveProfile.setOnClickListener {
            if (checking()) {
                binding.constraintLayout3.visibility = View.VISIBLE
                binding.constraintLayout2.alpha = 0.1f
                uploadImage()
            } else {
                Toast.makeText(context, "fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        binding.imgUser.setImageURI(it)
    }

    private fun checking(): Boolean {
        if (fname.text.trim { it <= ' ' }.toString().isNotEmpty() && lname.text.trim { it <= ' ' }
                .toString().isNotEmpty()) {
            return true
        }
        return false
    }

    private fun initVars() {
        storageRef = FirebaseStorage.getInstance().reference.child("Profile_Picture")
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun uploadImage() {
        binding.constraintLayout3.visibility = View.VISIBLE
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        if (imageUri != null) {
            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageRef.downloadUrl.addOnSuccessListener { uri ->

                            val map = hashMapOf(
                                "profilepicture" to uri.toString(),
                                "firstname" to fname.text.toString(),
                                "lastname" to lname.text.toString(),
                                "emailid" to email,
                            )
                            val users = firebaseFirestore.collection("EditProfile")
                            users.whereEqualTo("emailid", email).get()
                                .addOnSuccessListener {
                                    if (it.isEmpty) {
                                        firebaseFirestore.collection("EditProfile").document().get()
                                            .addOnCompleteListener { firestoreTask ->
                                                if (firestoreTask.isSuccessful) {
                                                    users.document(email).set(map)
                                                    Toast.makeText(
                                                        context,
                                                        "Uploaded Successfully",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    binding.constraintLayout2.alpha = 1f
                                                    binding.constraintLayout3.visibility = View.GONE

                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        firestoreTask.exception?.message,
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                }
                                            }
                                    } else {
                                        firebaseFirestore.collection("EditProfile").document(email)
                                            .update(
                                                map as Map<String, Any>
                                            )
                                        Toast.makeText(
                                            context,
                                            "Updated Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.constraintLayout2.alpha = 1f
                                        binding.constraintLayout3.visibility = View.GONE
                                        binding.imgUser.setImageResource(R.drawable.test_image)
                                    }
                                }
                        }
                    } else {
                        binding.constraintLayout2.alpha = 1f
                        Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                        binding.constraintLayout3.visibility = View.GONE
                    }
                }
            }

        } else {
            val map = hashMapOf(
                "firstname" to fname.text.toString(),
                "lastname" to lname.text.toString(),
                "emailid" to email,
            )
            val users = firebaseFirestore.collection("EditProfile")
            users.whereEqualTo("emailid", email).get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        firebaseFirestore.collection("EditProfile").document().get()
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    users.document(email).set(map)
                                    Toast.makeText(
                                        context,
                                        "Uploaded Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.constraintLayout2.alpha = 1f
                                    binding.constraintLayout3.visibility = View.GONE

                                } else {
                                    Toast.makeText(
                                        context,
                                        firestoreTask.exception?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                    } else {
                        firebaseFirestore.collection("EditProfile").document(email)
                            .update(
                                map as Map<String, Any>
                            )
                        Toast.makeText(
                            context,
                            "Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.constraintLayout2.alpha = 1f
                        binding.constraintLayout3.visibility = View.GONE
                        binding.imgUser.setImageResource(R.drawable.test_image)
                    }
                }
        }
    }
}

