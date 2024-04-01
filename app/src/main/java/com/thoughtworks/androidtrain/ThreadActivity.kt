package com.thoughtworks.androidtrain

import android.os.AsyncTask
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThreadActivity : AppCompatActivity() {
    private lateinit var button: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thread)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initialCounter()
    }

    private fun initialCounter() {
        button = findViewById(R.id.counter_button)
        button.setOnClickListener {
            CounterTask(button).execute()
        }
    }

    private class CounterTask(val button: AppCompatButton) : AsyncTask<Int, Int, Unit>() {
        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            button.isEnabled = true
            button.text = "0"
        }

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            button.isEnabled = false
        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values.first()?.let {
                button.text = "$it"
            }
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg p0: Int?) {
            repeat(11) {
                try {
                    Thread.sleep(1000)
                    publishProgress(it + 1)
                } catch (_: InterruptedException) {
                }
            }
        }
    }
}