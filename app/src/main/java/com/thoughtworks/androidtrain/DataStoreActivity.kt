package com.thoughtworks.androidtrain

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreActivity : AppCompatActivity() {
    private val button = lazy { findViewById<AppCompatButton>(R.id.ok_button) }
    private val textView = lazy { findViewById<TextView>(R.id.promotion_view) }
    private val preferences =
        lazy {
            applicationContext.dataStore.data.map {
                val isHintShow = it[DataStoreActivityPreferencesKeys.IS_HINT_SHOW] ?: false
                DataStoreActivityPreferences(isHintShow)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_store)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            preferences.value.collect {
                setupHint(it)
            }
        }
        handleClick {
            applicationContext.dataStore.edit {
                it[DataStoreActivityPreferencesKeys.IS_HINT_SHOW] = true
            }
        }
    }

    private fun setupHint(preferences: DataStoreActivityPreferences?) {
        if (preferences?.isHintShow == true) {
            textView.value.text = getString(R.string.welcome_back)
            button.value.apply {
                visibility = GONE
            }
        } else {
            button.value.apply {
                visibility = VISIBLE
            }
        }
    }

    private fun handleClick(block: suspend () -> Unit) {
        button.value.setOnClickListener {
            lifecycleScope.launch {
                block()
            }
            finish()
        }
    }
}
