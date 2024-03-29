package com.thoughtworks.androidtrain

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
        initToolbar()
        initialButtonListener()
        showAndroidFragmentByDefault()
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
    private fun showAndroidFragmentByDefault() {
        val androidFragment = LongTextFragment(resources.getString(R.string.android_desc))
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_view, androidFragment)
            .commit()
    }
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val androidFragment = LongTextFragment(resources.getString(R.string.android_desc))
        val javaFragment = LongTextFragment(resources.getString(R.string.java_desc), Gravity.BOTTOM)
        when(item.itemId) {
            R.id.android_menu -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_view, androidFragment)
                    .commit()
            }
            R.id.java_menu ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_view, javaFragment)
                    .commit()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}