package com.rk.notes

import RealTimeDatabase
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.rk.notes.Fragments.RetriveFromRealTime

@Suppress("DEPRECATION")
class Home : AppCompatActivity() {
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navController.popBackStack(R.id.action_realTimeDatabase_to_retriveFromRealTime, false)) {
            super.onBackPressed()
        }
        else {
            super.onBackPressed()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.logout -> {
                    FirebaseAuth.getInstance().signOut()
                    clearSharedPreferences(this)
                    startActivity(Intent(this, Login::class.java))
                    finish()
                }
                R.id.exit -> {
                    finish()
                }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun clearSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("my_pre", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}