package com.thoughtworks.androidtrain

import android.widget.Button
import android.widget.CheckBox
import com.google.common.truth.Truth.assertThat

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {
    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `should into login activity and remember me is enabled`() {
        val loginButton = activity.findViewById<Button>(R.id.login_button_view_id)
        assertThat(loginButton).isNotNull()

        loginButton.performClick()


        val shadowActivity = Shadows.shadowOf(activity)
        val nextIntent = shadowActivity.nextStartedActivity
        val shadowIntent = Shadows.shadowOf(nextIntent)
        assertThat(shadowIntent.intentClass).isEqualTo(LoginActivity::class.java)

        val loginActivity = Robolectric.buildActivity(LoginActivity::class.java).create().get()
        val rememberMe = loginActivity.findViewById<CheckBox>(R.id.remember_me)

        rememberMe.performClick()

        assertThat(rememberMe.isChecked).isTrue()
    }
}