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
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thoughtworks.androidtrain.compose.TweetListComposeActivity
import com.thoughtworks.androidtrain.tweet.ui.TweetsActivity
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
            listOf(
                "constraint",
                "login",
                "pick_contact",
                "fragment",
                "RecyclerView",
                "thread",
                "DataStore",
                "tweet_list_compose"
            )
        repeat(10) {
            val name =
                if (buttonNames.size > it) buttonNames[it] else resources.getString(R.string.button_name) + " ${it + 1}"
            val button = Button(this).apply {
                id = if (name == "login") R.id.login_button_view_id else View.generateViewId()
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
            "constraint" -> View.OnClickListener { jumpToActivity(ConstraintActivity::class.java) }
            "login" -> View.OnClickListener { jumpToActivity(LoginActivity::class.java) }
            "pick_contact" -> View.OnClickListener { contactSelectedLauncher.launch(null) }
            "fragment" -> View.OnClickListener { jumpToActivity(LanguageSelectionActivity::class.java) }
            "RecyclerView" -> View.OnClickListener { jumpToActivity(TweetsActivity::class.java) }
            "thread" -> View.OnClickListener { jumpToActivity(ThreadActivity::class.java) }
            "DataStore" -> View.OnClickListener { jumpToActivity(DataStoreActivity::class.java) }
            "tweet_list_compose" -> View.OnClickListener { jumpToActivity(TweetListComposeActivity::class.java) }
            else -> null
        }
        target?.let { button.setOnClickListener(it) }
    }

    private fun jumpToActivity(cls: Class<out ComponentActivity>) {
        startActivity(Intent(this, cls))
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
