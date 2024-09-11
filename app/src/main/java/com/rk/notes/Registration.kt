package com.rk.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.rk.notes.databinding.ActivityRegistration2Binding

class Registration : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegistration2Binding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistration2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        addOnClickListener()
    }

    private fun addOnClickListener() {
        binding.click.setOnClickListener(this)
        binding.register1.setOnClickListener(this)
    }

    private fun init() {
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.click -> {
                startActivity(Intent(this, Login::class.java))
            }

            R.id.register1 -> {
                if (binding.edFirstName
                        .text.isEmpty() && binding.pwd.text.isEmpty()
                ) {
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
                } else {
                    val email1 = binding.edFirstName.text.trim { it <= ' ' }.toString()
                    val password1 = binding.pwd.text.trim { it <= ' ' }.toString()
                    auth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, Login::class.java))
                                Toast.makeText(
                                    this,
                                    getString(R.string.success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        }
    }
}