package com.rk.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rk.notes.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        binding.click.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
            FirebaseAuth.getInstance().signOut()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.google1.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
        binding.login.setOnClickListener {
            if (binding.edFirstName.text.isEmpty() && binding.pwd.text.isEmpty()) {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
            } else {
                val email1 = binding.edFirstName.text.trim { it <= ' ' }.toString()
                val password1 = binding.pwd.text.trim { it <= ' ' }.toString()
                auth.signInWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            savetosharedPreferences()
//                            val sharedpref = getSharedPreferences("my_pre", Context.MODE_PRIVATE)
//                            val editor = sharedpref.edit()
//                            editor.putString("email",email1)
//                            editor.putString("password",password1)
//                            editor.apply()
                            startActivity(Intent(this, Home::class.java))
                            Toast.makeText(this, "login successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            savetosharedPreferences()
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    private fun savetosharedPreferences()
    {
        val sharedpref = getSharedPreferences("my_pre", Context.MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.putString("email",binding.edFirstName.text.toString())
        editor.putString("password",binding.pwd.text.toString())
        editor.apply()
    }
}