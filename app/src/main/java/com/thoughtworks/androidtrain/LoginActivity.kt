package com.thoughtworks.androidtrain

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("Login","LoginActivity started")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Login","LoginActivity resumed")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Login","LoginActivity stopped")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Login","LoginActivity paused")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Login","LoginActivity destroyed")
    }
}