package com.example.craftzone.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.craftzone.Data.ProductItem
import com.example.craftzone.R
import com.example.craftzone.databinding.ActivityReceiveAddToCartBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@Suppress("DEPRECATION")
class ReceiveAddToCart : AppCompatActivity() {
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var price1: TextView
    private lateinit var image: ImageView
    private lateinit var sharedpref: SharedPreferences
    private lateinit var email: String
    private lateinit var binding: ActivityReceiveAddToCartBinding
    private var uri: Uri? = null
    private lateinit var ImageString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiveAddToCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "About Page"


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

        val imageView = food?.image
        uri = Uri.parse(imageView)
        Glide.with(this).load(uri).into(image)

        val imgEmail: ImageView = findViewById(R.id.img_email)
        val imgPhone: ImageView = findViewById(R.id.img_phone)
        imgEmail.setOnClickListener { openEmailApp() }
        imgPhone.setOnClickListener { openCallApp() }

        storageRef = FirebaseStorage.getInstance().reference.child("OnItemClickListener")
        firebaseFirestore = FirebaseFirestore.getInstance()

        binding.deleteToCart.setOnClickListener {
            uploadImage()
            binding.constraint.alpha = 0.3F
            binding.progressBar2.visibility = View.VISIBLE
            binding.wait.visibility = View.VISIBLE
            binding.constraint1.visibility = View.VISIBLE
        }
    }
    private fun openCallApp() {
        Intent(Intent.ACTION_DIAL, Uri.parse("tel:9023978985")).also { startActivity(it) }
    }

    private fun openEmailApp() {
//        Intent(Intent.ACTION_SEND).apply {
//            type = "text/plain"
//            putExtra(Intent.EXTRA_EMAIL, arrayOf("vipuljadav542@gmail.com")) // recipients
//            putExtra(Intent.EXTRA_SUBJECT, "")
//            putExtra(Intent.EXTRA_TEXT, "")
//            putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
//        }.also { startActivity(it) }
        val emailIntent = Intent(Intent.ACTION_SENDTO)

        // Set the data for the email intent
        emailIntent.data = Uri.parse("mailto:") // Use "mailto:" as the URI scheme
        // Optionally, set the subject and body of the email
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("vipuljadav542@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(emailIntent)
        // Check if there's an app that can handle this intent
//        if (emailIntent.resolveActivity(packageManager) != null) {
//            // Start the activity with the email intent
//            startActivity(emailIntent)
//        } else {
//            // Handle the case where there is no email app installed
//            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun uploadImage() {
        if (ImageString.isNotEmpty()) {
            val productQuery = firebaseFirestore.collection("AddtoCart")
                .whereEqualTo("pic", uri.toString())
                .get()
            productQuery.addOnSuccessListener {
                for (document in it) {
                    firebaseFirestore.collection("AddtoCart").document(document.id).delete()
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "product deleted from cart",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.constraint.alpha = 1F
                            binding.progressBar2.visibility = View.INVISIBLE
                            binding.wait.visibility = View.INVISIBLE
                            binding.constraint1.visibility = View.INVISIBLE
                            startActivity(Intent(this, Home::class.java))
                            finish()
                        }
                }
            }
        } else {
            binding.constraint.alpha = 1F
            binding.progressBar2.visibility = View.INVISIBLE
            binding.wait.visibility = View.INVISIBLE
            binding.constraint1.visibility = View.INVISIBLE
            Toast.makeText(this@ReceiveAddToCart, "Something wen't Wrong!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}