package com.thoughtworks.androidtrain

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SharedPreferenceActivity : AppCompatActivity() {
    private val button = lazy { findViewById<AppCompatButton>(R.id.ok_button) }
    private val textView = lazy { findViewById<TextView>(R.id.promotion_view) }
    private val sharedRef = lazy {
        applicationContext.getSharedPreferences(
            getString(R.string.preference_show_key),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shared_preference)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        handleHint()
        handleClick()
    }

    private fun handleHint() {
        val isHintShow = sharedRef.value.getBoolean(getString(R.string.is_hint_show), false)
        if (isHintShow) {
            textView.value.text = getString(R.string.welcome_back)
            button.value.apply {
                visibility = View.GONE
            }
        } else {
            button.value.apply {
                visibility = View.VISIBLE
            }
        }
    }

    private fun handleClick() {
        button.value.setOnClickListener {
            with(sharedRef.value.edit()) {
                this.putBoolean(getString(R.string.is_hint_show), true)
                /**
                 * commit()是同步的，会阻塞主线程，有返回值，会立即更改内存中的对象值，返回成功结果
                 */
                if (this.commit()) {
                    this@SharedPreferenceActivity.finish()
                }
                /**
                 * apply是异步的，原子性，不阻塞线程
                 */
//                apply()
            }
        }
    }
}