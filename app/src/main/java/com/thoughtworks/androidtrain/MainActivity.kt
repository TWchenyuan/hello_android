package com.thoughtworks.androidtrain

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thoughtworks.androidtrain.tweet.TweetsActivity
import com.thoughtworks.androidtrain.util.fetchContact

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initButton() {
        val layout = findViewById<LinearLayout>(R.id.button_list)
        val buttonNames =
            listOf("constraint", "login", "pick_contact", "fragment", "RecyclerView", "thread")
        repeat(10) {
            val name =
                if (buttonNames.size > it) buttonNames[it] else resources.getString(R.string.button_name) + " ${it + 1}"
            val button = Button(this).apply {
                layoutParams = LayoutParams(250.dp, LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                    topMargin = 40.dp
                }
                text = name
                isAllCaps = false
                background =
                    ContextCompat.getDrawable(this@MainActivity, R.drawable.button_background)
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
                backgroundTintList = null
            }
            handleClicker(name, button)
            layout.addView(button)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleClicker(key: String, button: Button) {
        val target = when (key) {
            "constraint" -> View.OnClickListener {
                startActivity(Intent(this, ConstraintActivity::class.java))
            }

            "login" -> View.OnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            "pick_contact" -> View.OnClickListener {
                contactSelectedLauncher.launch(null)
            }

            "fragment" -> View.OnClickListener {
                startActivity(Intent(this, LanguageSelectionActivity::class.java))
            }

            "RecyclerView" -> View.OnClickListener {
                startActivity(Intent(this, TweetsActivity::class.java))
            }

            "thread" -> View.OnClickListener {
                startActivity(Intent(this, ThreadActivity::class.java))
            }

            else -> null
        }
        target?.let { button.setOnClickListener(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val contactSelectedLauncher =
        registerForActivityResult(ActivityResultContracts.PickContact()) {
            it?.let { uri ->
                contentResolver.fetchContact(uri)?.let {
//                    Toast.makeText(this, "${it.first} ${it.second}", Toast.LENGTH_SHORT).show()
                    showDialog(it.first, it.second)
                }
            }
        }

    private fun showDialog(name: String, phone: String) {
        val dialog = AlertDialog.Builder(this).apply {
            setMessage(
                """
                $name
                $phone
            """.trimIndent()
            )
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            setCancelable(false)
        }.create()
        dialog.show()
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
