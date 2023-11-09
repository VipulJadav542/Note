package com.example.craftzone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.craftzone.R
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        auth = FirebaseAuth.getInstance()
        val registeredEmail = findViewById<EditText>(R.id.registeredEmail)
        val sendEmail = findViewById<Button>(R.id.sendEmail)

        sendEmail.setOnClickListener {
            if (registeredEmail.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                val email = registeredEmail.text.toString()
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(this, "please check your email", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Login::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
            else
            {
                Toast.makeText(this,"plese enter valid email", Toast.LENGTH_SHORT).show()
            }
        }
        registeredEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(registeredEmail.text.toString())
                        .matches()
                ) {
                    sendEmail.isEnabled = true
                } else {
                    sendEmail.isEnabled = false
                    registeredEmail.error = "Invalid Email"
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}