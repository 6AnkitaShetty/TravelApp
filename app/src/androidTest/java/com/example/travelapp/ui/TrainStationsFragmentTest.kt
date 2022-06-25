package com.example.travelapp.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.travelapp.presentation.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.travelapp.R
import com.example.travelapp.launchFragmentInHiltContainer
import com.example.travelapp.presentation.ui.TrainStationsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@HiltAndroidTest
class TrainStationsFragmentTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_isStationItemsVisibleOnAppLaunch() {
        launchFragmentInHiltContainer<TrainStationsFragment>()
        Espresso.onView(withId(R.id.rvTrainStations)).check(ViewAssertions.matches(withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE)))
    }
}