package com.dang.marty.weather

import android.app.Instrumentation
import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.dang.marty.weather.presentation.presenter.DailyWeatherPresenter
import com.dang.marty.weather.presentation.view.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class DailyWeatherFragTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var instrumentation: Instrumentation
    private lateinit var targetContext: Context

    @Mock
    private lateinit var presenter: DailyWeatherPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        hiltRule.inject()

        instrumentation = InstrumentationRegistry.getInstrumentation()
        targetContext = instrumentation.targetContext
    }

    @Test
    fun test_example() {
        onView(withId(R.id.seekbar))
    }


}