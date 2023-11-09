package com.example.craftzone.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.craftzone.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("NAME_SHADOWING")
class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var click: TextView
    private lateinit var animation: LottieAnimationView
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var google: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        val login = findViewById<Button>(R.id.login)

        val email12 = findViewById<EditText>(R.id.edFirstName)
        email12.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email12.text.toString())
                        .matches()
                ) {
                    login.isEnabled = true
                } else {
                    login.isEnabled = false
                    email12.error = "Invalid Email"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        animation = findViewById(R.id.animation_view)
        //for forgot Password
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ResetPassword::class.java))
        }

        db = FirebaseFirestore.getInstance()

        //sign  in with google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        google = findViewById(R.id.google1)
        google.setOnClickListener {
            animation.visibility = View.VISIBLE
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }

        val sharedpref = getSharedPreferences("my_pre", Context.MODE_PRIVATE)
        val email1 = sharedpref.getString("email", "")
        if (email1 != "") {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        click = findViewById(R.id.click)
        click.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
            finish()
        }
        findViewById<EditText>(R.id.edFirstName)
        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            if (checkForInternet(this)) {
                if (checking()) {
                    animation.visibility = View.VISIBLE
                    val email = email12.text.toString()
                    val password = findViewById<EditText>(R.id.pwd).text.toString()
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT)
                                    .show()
                                val sharedpref =
                                    getSharedPreferences("my_pre", Context.MODE_PRIVATE)
                                val editor = sharedpref.edit()
                                editor.putString("email", email)
                                editor.putString("password", password)
                                editor.apply()
                                val intent = Intent(this, Home::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                animation.visibility = View.GONE
                                Toast.makeText(
                                    this,
                                    "Invalid email or password!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Please enter valid email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "please turn on the internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checking(): Boolean {
        val email = findViewById<TextView>(R.id.edFirstName)
        val password = findViewById<TextView>(R.id.pwd)
        if (email.text.toString().trim { it <= ' ' }.isNotEmpty() && password.text.toString()
                .trim { it <= ' ' }.isNotEmpty()
        ) {
            return true
        }
        Toast.makeText(this,"enter valid email and password", Toast.LENGTH_SHORT).show()
        return false
    }

    //for checking internet connection
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            animation.visibility = View.GONE
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    val user = hashMapOf(
                        "Name" to "fname",
                        "Mobile" to "lname",
                        "email" to account?.email
                    )
                    val users = db.collection("USERS")
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {

                            val sharedpref = getSharedPreferences("my_pre", Context.MODE_PRIVATE)
                            val editor = sharedpref.edit()
                            editor.putString("email", account?.email.toString())
                            editor.apply()
                            users.document(account?.email.toString()).set(user)
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
}