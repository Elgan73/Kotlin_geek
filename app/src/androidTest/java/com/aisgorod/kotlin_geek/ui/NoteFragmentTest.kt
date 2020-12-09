package com.aisgorod.kotlin_geek.ui

import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.aisgorod.kotlin_geek.R
import com.aisgorod.kotlin_geek.data.db.FireStoreDatabaseProvider
import com.aisgorod.kotlin_geek.model.Note
import com.aisgorod.kotlin_geek.presentation.NoteViewModel
import com.aisgorod.kotlin_geek.presentation.SplashViewModel
import io.mockk.every
import org.junit.After
import org.junit.Before
import org.junit.Test
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.rules.TestRule

import org.junit.runner.RunWith
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class NoteFragmentTest {

//    @get:Rule
//    val activityTestRule = ActivityTestRule(SplashActivity::class.java, true, false)

    private val mockViewModel: NoteViewModel = mockk(relaxed = true)


    @Before
    fun setup() {
        kotlin.runCatching { startKoin {  } }

        loadKoinModules(
            module {
                viewModel { (_: Note?) ->
                    mockViewModel
                }
            }
        )
    }

    @After
    fun clean() {
        stopKoin()
    }

//    @Test
//    fun testEventFragment() {
//
//        every { mockViewModel.saveNote() } returns Unit
//
//        activityTestRule.launchActivity(null)
//        onView(withId(R.id.startPicture)).check { view, _ ->
//            assertTrue((view as ImageView).isVisible )
//
//        }
//    }


    @Test
    fun save_note_on_button_click() {
        launchFragmentInContainer<NoteFragment>(themeResId = R.style.Theme_AppCompat)

        onView(withId(R.id.saveNoteBtn)).perform(click())

        verify { mockViewModel.saveNote() }
    }
}