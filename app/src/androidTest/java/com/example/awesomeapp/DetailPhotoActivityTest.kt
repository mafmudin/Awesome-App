package com.example.awesomeapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

class DetailPhotoActivityTest{
    @get:Rule
    val intentsTestRule = IntentsTestRule(DetailPhotoActivity::class.java)

    @Test
    fun photo_url_click(){
        onView(withId(R.id.tvPhotoUrl)).perform(click())
        intended(allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData("https://")));
    }
}