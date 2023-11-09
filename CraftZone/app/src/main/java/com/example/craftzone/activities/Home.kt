package com.example.craftzone.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.craftzone.R
import com.example.craftzone.fragments.bottomNavigation.AddToCart
import com.example.craftzone.fragments.bottomNavigation.HomeFragment
import com.example.craftzone.fragments.bottomNavigation.ProfileFragment
import com.example.craftzone.fragments.bottomNavigation.SearchFragment
import com.example.craftzone.fragments.drawer.Developers_Contact
import com.example.craftzone.fragments.drawer.about
import com.example.craftzone.fragments.drawer.chair
import com.example.craftzone.fragments.drawer.curtain_blind
import com.example.craftzone.fragments.drawer.decorative_panels
import com.example.craftzone.fragments.drawer.genuine_leather
import com.example.craftzone.fragments.drawer.mattress
import com.example.craftzone.fragments.drawer.modular_kitchen
import com.example.craftzone.fragments.drawer.pvc_wooden_flooring
import com.example.craftzone.fragments.drawer.recliner
import com.example.craftzone.fragments.drawer.room_partition
import com.example.craftzone.fragments.drawer.sofa
import com.example.craftzone.fragments.drawer.sofa_with_bed
import com.example.craftzone.fragments.drawer.texture
import com.example.craftzone.fragments.drawer.upholstery_bed
import com.example.craftzone.fragments.drawer.wardrobe
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION")
class Home : AppCompatActivity() {
    private lateinit var Toogle: ActionBarDrawerToggle
    lateinit var drawer: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    companion object {
        var instance: Home? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        instance = this

        val sharedpref = getSharedPreferences("my_pre", Context.MODE_PRIVATE)
        sharedpref.getString("email", "")

        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        //for bottomnavigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home ->
                    replaceFragments(HomeFragment())

                R.id.search ->
                    replaceFragments(SearchFragment())

                R.id.cart ->
                    replaceFragments(AddToCart())

                R.id.profile ->
                    replaceFragments(ProfileFragment())

                else -> {
                }
            }
            val navigationView: NavigationView = findViewById(R.id.navigationView)
            navigationView.setCheckedItem(it.itemId)
            true
        }
        drawer = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.navigationView)

        Toogle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(Toogle)
        Toogle.syncState()

        if (savedInstanceState == null) {
            replaceFragments(HomeFragment())
            navigationView.setCheckedItem(R.id.home)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about -> replaceFragments(about())
                R.id.chair -> replaceFragments(chair())
                R.id.curtain_blind -> replaceFragments(curtain_blind())
                R.id.genuine_leather -> replaceFragments(genuine_leather())
                R.id.mattress -> replaceFragments(mattress())
                R.id.modular_kitchen -> replaceFragments(modular_kitchen())
                R.id.pvc_wooden_flooring -> replaceFragments(pvc_wooden_flooring())
                R.id.recliner -> replaceFragments(recliner())
                R.id.room_partition -> replaceFragments(room_partition())
                R.id.sofa -> replaceFragments(sofa())
                R.id.sofa_with_bed -> replaceFragments(sofa_with_bed())
                R.id.texture -> replaceFragments(texture())
                R.id.upholstery_bed -> replaceFragments(upholstery_bed())
                R.id.wardrobe -> replaceFragments(wardrobe())
                R.id.decorative_anels -> replaceFragments(decorative_panels())
                R.id.developercontact -> replaceFragments(Developers_Contact())
            }
            drawer.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }

    private fun replaceFragments(fragment: Fragment) {
        drawer.closeDrawer(GravityCompat.START)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (Toogle.onOptionsItemSelected(item)) {
            return true
        } else {
            when (item.itemId) {
                R.id.action_share -> shareApp()
                R.id.action_feedback -> email_send()
                R.id.action_exit -> exit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exit() {
        finishAffinity()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun email_send() {
//        val intent = Intent(Intent.ACTION_SENDTO).apply {
//            data = Uri.parse("mailto:vipuljadav542@gmail.com")
//        }
//        if (intent.resolveActivity(packageManager) != null) {
//            startActivity(intent)
//        } else {
//            Toast.makeText(this, "required app is not installed", Toast.LENGTH_SHORT).show()
//        }
        // Create an intent with the action set to send
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        // Set the data for the email intent
        emailIntent.data = Uri.parse("mailto:") // Use "mailto:" as the URI scheme
        // Optionally, set the subject and body of the email
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("vipuljadav542@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")

        startActivity(emailIntent)
    }

    private fun shareApp() {
        val message =
            "you can download app from https://vipuljadav542.000webhostapp.com/tutorial/My%20Application/CraftZone.apk"
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "select app"))
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onDestroy() {
        super.onDestroy()

        // Reset the instance when the activity is destroyed
        instance = null
    }
}