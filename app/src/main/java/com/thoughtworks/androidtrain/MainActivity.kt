package com.thoughtworks.androidtrain

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initButton()
    }

    private fun initButton() {
        val layout = findViewById<LinearLayout>(R.id.button_list)
        repeat(10) {
            val button = Button(this).apply {
                val name = resources.getString(R.string.button_name) + " ${it + 1}"
                layoutParams = LayoutParams(250.dp, LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                    topMargin = 40.dp
                }
                text = name
                isAllCaps = false
                background = ContextCompat.getDrawable(this@MainActivity, R.drawable.button_background)
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
                backgroundTintList = null
            }
            if (it == 4) {
                button.setOnClickListener {
                    startActivity(Intent(this, RelativeActivity::class.java))
                }
            }
            layout.addView(button)
        }
    }

    val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
