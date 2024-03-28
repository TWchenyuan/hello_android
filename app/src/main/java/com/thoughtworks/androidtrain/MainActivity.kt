package com.thoughtworks.androidtrain

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thoughtworks.androidtrain.util.fetchContact

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
        val buttonNames = listOf("constraint", "login", "pick_contact")
        repeat(10) {
            val button = Button(this).apply {
                val name =
                    if (buttonNames.size > it) buttonNames[it] else resources.getString(R.string.button_name) + " ${it + 1}"
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
            if (it == 0) {
                button.setOnClickListener {
                    startActivity(Intent(this, ConstraintActivity::class.java))
                }
            }
            if (it == 1) {
                button.setOnClickListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            if (it == 2) {
                button.setOnClickListener {
                    contactSelectedLauncher.launch(null)
                }
            }
            layout.addView(button)
        }
    }

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
        }.create()
        dialog.show()
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
