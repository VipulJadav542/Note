package com.rk.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.rk.notes.databinding.ActivityRegistration2Binding

class Registration : AppCompatActivity() {
    private lateinit var binding: ActivityRegistration2Binding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistration2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()
        auth=FirebaseAuth.getInstance()
        binding.register1.setOnClickListener {
            if (binding.edFirstName
                .text.isEmpty() && binding.pwd.text.isEmpty()) {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
            } else {
                val email1 = binding.edFirstName.text.trim { it <= ' ' }.toString()
                val password1 = binding.pwd.text.trim { it <= ' ' }.toString()
                auth.createUserWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this,Login::class.java))
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.click.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }

    }
}