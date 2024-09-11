package com.rk.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.rk.notes.utils.Const.EMAIL
import com.rk.notes.utils.Const.MY_PRE
import com.rk.notes.utils.Const.PASSWORD

class Login : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        addOnClickListener()
    }

    private fun addOnClickListener() {
        binding.click.setOnClickListener(this)
        binding.login.setOnClickListener(this)
        binding.google1.setOnClickListener(this)
    }

    private fun init() {
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        initGoogleSignInClient()
    }

    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                            Toast.makeText(
                                this,
                                getString(R.string.login_successful), Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.login_failed), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.failed_), Toast.LENGTH_SHORT).show()
            }
        }

    private fun savetosharedPreferences() {
        val sharedpref = getSharedPreferences(MY_PRE, Context.MODE_PRIVATE)
        val editor = sharedpref.edit()
        editor.putString(EMAIL, binding.edFirstName.text.toString())
        editor.putString(PASSWORD, binding.pwd.text.toString())
        editor.apply()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.click -> {
                startActivity(Intent(this, Registration::class.java))
                FirebaseAuth.getInstance().signOut()
            }

            R.id.login -> {
                if (binding.edFirstName.text.isEmpty() && binding.pwd.text.isEmpty()) {
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
                } else {
                    val email1 = binding.edFirstName.text.trim { it <= ' ' }.toString()
                    val password1 = binding.pwd.text.trim { it <= ' ' }.toString()
                    auth.signInWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                savetosharedPreferences()
                                startActivity(Intent(this, Home::class.java))
                                Toast.makeText(
                                    this,
                                    getString(R.string.login_successfully), Toast.LENGTH_SHORT
                                )
                                    .show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    getString(R.string.failed__), Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }

            R.id.google1 -> {
                val signInClient = googleSignInClient.signInIntent
                launcher.launch(signInClient)
            }
        }
    }
}