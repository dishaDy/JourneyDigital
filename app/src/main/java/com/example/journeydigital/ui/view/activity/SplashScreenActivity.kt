package com.example.journeydigital.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.journeydigital.R

/**
 * Created by Disha Shah
 */

class SplashScreenActivity : AppCompatActivity() {
    /**
     * Initial onCreate method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)
        navigateToDashboard()
    }

    /**
     * After 3000 ms redirected to dashboard
     */
    private fun navigateToDashboard() {
        Handler(Looper.getMainLooper()).postDelayed({
            val dashboardIntent = Intent(this, AppMainActivity::class.java)
            startActivity(dashboardIntent)
            finish()
        }, 3000)

    }
}