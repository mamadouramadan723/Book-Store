package com.rmd.media.bookstore.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.rmd.media.bookstore.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState);

    makeFullScreen()

    setContentView(R.layout.activity_splash)


    val SPLASH_SCREEN = 400

    Handler(Looper.getMainLooper()).postDelayed({
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)

      // Animate the loading of new activity
      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

      finish()
    }, SPLASH_SCREEN.toLong())
    // Using a handler to delay loading the MainActivity

  }

  private fun makeFullScreen() {
    // Remove Title
    requestWindowFeature(Window.FEATURE_NO_TITLE)

    // Make Fullscreen
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)

    // Hide the toolbar
    supportActionBar?.hide()
  }
}
