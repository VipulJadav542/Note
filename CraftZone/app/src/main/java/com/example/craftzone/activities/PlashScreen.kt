package com.example.craftzone.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.craftzone.R
import dagger.hilt.android.AndroidEntryPoint


class PlashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plash)

//        val image1=findViewById<ImageView>(R.id.imageView1)
//        val image3=findViewById<ImageView>(R.id.imageView3)
//        val image4=findViewById<ImageView>(R.id.imageView4)
//        val image5=findViewById<ImageView>(R.id.imageView5)
//        val image6=findViewById<ImageView>(R.id.imageView9)
//        val image7=findViewById<ImageView>(R.id.imageView7)
//        val image8=findViewById<ImageView>(R.id.imageView8)
//        val logo=findViewById<ImageView>(R.id.logo)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            image1.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.blink)
////            image1.startAnimation(anim)
//        },100)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            image3.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.blink)
////            image3.startAnimation(anim)
//        },100)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            image4.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.blink)
////            image4.startAnimation(anim)
//        },100)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            image5.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.blink)
////            image5.startAnimation(anim)
//        },100)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            image6.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.blink)
////            image6.startAnimation(anim)
//        },100)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            image7.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.blink)
////            image7.startAnimation(anim)
//        },100)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            image8.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.blink)
////            image8.startAnimation(anim)
//        },100)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            logo.visibility= View.VISIBLE
//            val anim=AnimationUtils.loadAnimation(this, R.anim.scale)
//            logo.startAnimation(anim)
//        },100)
////        Handler(Looper.getMainLooper()).postDelayed({
////            image9.visibility= View.VISIBLE
////            val anim=AnimationUtils.loadAnimation(this, R.anim.scale)
////            logo.startAnimation(anim)
////        },2100)

        val button=findViewById<ImageView>(R.id.button)
//        Handler(Looper.getMainLooper()).postDelayed({
            button.visibility= View.VISIBLE
            val anim=AnimationUtils.loadAnimation(this, R.anim.scale)
            button.startAnimation(anim)
            button.setOnClickListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
//        },1000)
    }
}

