package com.thoughtworks.androidtrain

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thoughtworks.androidtrain.fragment.LongTextFragment

class LanguageSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_language_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialButtonListener()
    }

    private fun initialButtonListener() {
        val androidFragment = LongTextFragment(resources.getString(R.string.android_desc))
        val javaFragment = LongTextFragment(resources.getString(R.string.java_desc), Gravity.BOTTOM)
        val androidButton: Button = findViewById(R.id.android_button)
        androidButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_view, androidFragment)
                .commit()
        }
        val javaButton: Button = findViewById(R.id.java_button)
        javaButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_view, javaFragment)
                .commit()
        }
    }
}