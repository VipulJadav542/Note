package com.example.craftzone.activities.admin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.craftzone.Data.ProductItem
import com.example.craftzone.R
import com.example.craftzone.databinding.ActivityReceiveAllItemsBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@Suppress("DEPRECATION")
class deleteAdmin : AppCompatActivity() {
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var price1: TextView
    private lateinit var image: ImageView
    private lateinit var sharedpref: SharedPreferences
    private lateinit var email: String
    private lateinit var binding: ActivityReceiveAllItemsBinding
    private var uri: Uri? = null
    private lateinit var ImageString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiveAllItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Delete Page"

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        price1 = findViewById(R.id.price1)
        image = findViewById(R.id.image)

        sharedpref = getSharedPreferences("my_pre", Context.MODE_PRIVATE)
        email = sharedpref.getString("email", "").toString()

        val food = intent.getParcelableExtra<ProductItem>("food")

        title.text = food?.name
        price1.text = food?.price.toString()
        description.text = food?.des

        ImageString = food?.image.toString()

        uri = Uri.parse(ImageString)
        Glide.with(this).load(uri).into(image)

        firebaseFirestore = FirebaseFirestore.getInstance()
        storageRef=FirebaseStorage.getInstance().getReference("ProductPictures")

        binding.deleteToCart.setOnClickListener {
            uploadImage()
            binding.constraint.alpha = 0.3F
            binding.progressBar2.visibility = View.VISIBLE
            binding.wait.visibility = View.VISIBLE
            binding.constraint1.visibility = View.VISIBLE
        }
    }
    private fun uploadImage() {
        if (ImageString.isNotEmpty()) {
            val productQuery = firebaseFirestore.collection("ProductDetails")
                .whereEqualTo("pic", uri.toString())
                .get()
            productQuery.addOnSuccessListener {
                for (document in it) {
                    firebaseFirestore.collection("ProductDetails").document(document.id).delete()
//                    storageRef.child(uri.toString()).delete()
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "product deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                            deleteImageByUrl(uri.toString())
                            binding.constraint.alpha = 1F
                            binding.progressBar2.visibility = View.INVISIBLE
                            binding.wait.visibility = View.INVISIBLE
                            binding.constraint1.visibility = View.INVISIBLE
                            startActivity(Intent(this, ReceiveAdmin::class.java))
                            finish()
                        }
                }
            }
        } else {
            binding.constraint.alpha = 1F
            binding.progressBar2.visibility = View.INVISIBLE
            binding.wait.visibility = View.INVISIBLE
            binding.constraint1.visibility = View.INVISIBLE
            Toast.makeText(this@deleteAdmin, "Something wen't Wrong!", Toast.LENGTH_SHORT)
                .show()
        }
    }
    fun deleteImageByUrl(imageUrl: String) {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.getReferenceFromUrl(imageUrl)

        // Delete the file
        storageReference.delete()
            .addOnSuccessListener {
                // File deleted successfully
                // Handle success, e.g., update UI or show a message
                Log.d(ContentValues.TAG, "Image deleted successfully")
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occurred while deleting the file
                Log.e(ContentValues.TAG, "Error deleting image: $exception")
            }
    }
}