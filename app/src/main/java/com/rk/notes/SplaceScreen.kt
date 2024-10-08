package com.rk.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.rk.notes.utils.Const.EMAIL
import com.rk.notes.utils.Const.MY_PRE

class SplaceScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace_screen)

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedpref = getSharedPreferences(MY_PRE, Context.MODE_PRIVATE)
            val email1 = sharedpref.getString(EMAIL, null)
            if (email1 != null) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000)
    }
}