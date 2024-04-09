package com.thoughtworks.androidtrain

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()

    }
    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun should_into_login_activity_and_click_remember_me_to_checked() {
        onView(withId(R.id.login_button_view_id)).perform(click())

        intended(hasComponent(LoginActivity::class.java.name))

        onView(withId(R.id.remember_me)).perform(click()).check(matches(isChecked()))
    }
}