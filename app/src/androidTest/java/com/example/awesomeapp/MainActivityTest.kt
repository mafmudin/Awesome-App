package com.example.awesomeapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test


class MainActivityTest{

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun test_recycler_view(){
        onView(withId(R.id.rvPhotos))
            .perform(RecyclerViewActions
                .scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText("Mitya  Zotov"))))
    }

    @Test
    fun scroll_to_item_bellow_fold_check(){
        onView(withId(R.id.rvPhotos))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        onView(withText("Alex Kozlov")).check(matches(isDisplayed()))
    }

    @Test
    fun grid_view_click(){
        onView(withId(R.id.menuGrid)).perform(click())
    }

    @Test
    fun list_view_click(){
        onView(withId(R.id.menuList)).perform(click())
    }
}